package com.tinet.ctilink.control.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.rpc.RpcContext;
import com.tinet.ctilink.conf.model.SystemSetting;
import com.tinet.ctilink.inc.SystemSettingConst;
import com.tinet.ctilink.json.JSONObject;
import com.tinet.ctilink.util.AuthenticUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.tinet.ctilink.ami.action.AmiActionResponse;
import com.tinet.ctilink.ami.action.AmiActionService;
import com.tinet.ctilink.cache.CacheKey;
import com.tinet.ctilink.cache.RedisService;
import com.tinet.ctilink.conf.model.SipGroup;
import com.tinet.ctilink.conf.model.SipMediaServer;
import com.tinet.ctilink.conf.model.Trunk;
import com.tinet.ctilink.control.inc.ControlConst;
import com.tinet.ctilink.inc.Const;
import com.tinet.ctilink.util.ContextUtil;
import com.tinet.ctilink.util.PropertyUtil;

/**
 * @author fengwei //
 * @date 16/4/26 14:12
 * <p/>
 * 查询AmiActionService接口
 */

public class ActionServiceHelper {
    private static Logger logger = LoggerFactory.getLogger(ActionServiceHelper.class);

    private static final String AMI_DUBBO_APPLICATION_VERSION = "ami.dubbo.application.version";
    private static final String AMI_DUBBO_APPLICATION_TIMEOUT = "ami.dubbo.application.timeout";
    private static final String AMI_DUBBO_PROTOCOL_PORT = "ami.dubbo.protocol.prot";

    private static final Map<String, ReferenceConfig<AmiActionService>> services = new HashMap<>();
    private static final ApplicationConfig application = new ApplicationConfig("cti-link-control-client");
    private static final String APPLICATION_VERSION;  //版本
    private static final int APPLICATION_TIMEOUT;  //超时时间
    private static final String APPLICATION_PORT;
    private static RedisService redisService;

    static {
        redisService = ContextUtil.getBean(RedisService.class);

        APPLICATION_VERSION = PropertyUtil.getProperty(AMI_DUBBO_APPLICATION_VERSION);

        APPLICATION_TIMEOUT = Integer.parseInt(PropertyUtil.getProperty(AMI_DUBBO_APPLICATION_TIMEOUT));

        APPLICATION_PORT = PropertyUtil.getProperty(AMI_DUBBO_PROTOCOL_PORT);
    }

    //查询ami
    public static AmiActionService getService(Map<String, Object> params
            , AmiActionResponse amiActionResponse) {
        SipMediaServer sipMediaServer = null;
        if (!params.containsKey(ControlConst.PARAM_SIP_ID)) {  //不用区分哪个ami
            JSONObject actionEvent = (JSONObject) params.get(ControlConst.PARAM_ACTION_EVENT);
            String enterpriseId = actionEvent.get(ControlConst.PARAM_ENTERPRISE_ID).toString();
            if (!StringUtils.isNumeric(enterpriseId)) {
                amiActionResponse.setMsg("invalid enterpriseId");
                return null;
            }
            Trunk trunk = redisService.get(Const.REDIS_DB_CONF_INDEX, String.format(CacheKey.TRUNK_ENTERPRISE_ID_FIRST
                    , Integer.parseInt(enterpriseId)), Trunk.class);
            if (trunk == null) {
                amiActionResponse.setMsg("trunk first cache error");
                return null;
            }
            List<SipMediaServer> sipMediaServerList = redisService.getList(Const.REDIS_DB_CONF_INDEX
                    , CacheKey.SIP_MEDIA_SERVER, SipMediaServer.class);
            int sipGroupId = trunk.getSipGroupId();
            //不在任何一个group, 按照比例分配获取一个sipGroupId
            if (sipGroupId == -1) {
                List<SipGroup> sipGroupList = redisService.getList(Const.REDIS_DB_CONF_INDEX, CacheKey.SIP_GROUP
                        , SipGroup.class);
                //根据比例获取sipGroup
                int totalPercent = 0;
                //比例 10, 20, 30, 40
                //根据sipGroupList中元素的顺序, 0~9 10~39 40~69 70~109
                for (SipGroup sipGroup : sipGroupList) {
                    totalPercent += sipGroup.getPercent();
                    sipGroup.setPercent(totalPercent);
                }
                int key = RandomUtils.nextInt(0, totalPercent);
                for (SipGroup sipGroup : sipGroupList) {
                    if (key < sipGroup.getPercent()) {
                        sipGroupId = sipGroup.getId();
                        break;
                    }
                }
            }

            //确定了sipGroup, 从在sipGroup的sipMediaServerList中随机选一个sipMediaServer
            List<SipMediaServer> targetList = new ArrayList<>();
            for (SipMediaServer server : sipMediaServerList) {
                if (server.getGroupId().equals(sipGroupId)) {
                    targetList.add(server);
                }
            }
            if (targetList.isEmpty()) {
                amiActionResponse.setMsg("invalid sip group");
                return null;
            }
            //随机获取一个sipMediaServer
            sipMediaServer = targetList.get(RandomUtils.nextInt(0, targetList.size()));

        } else {  //direct ip, sipId确定
            try {
                Object obj = params.get(ControlConst.PARAM_SIP_ID);
                if (obj == null || !StringUtils.isNumeric(obj.toString())) {
                    amiActionResponse.setMsg("invalid sipId");
                    return null;
                }
                Integer sipId = Integer.parseInt(obj.toString());
                //处理完成, 删除sipId
                params.remove(ControlConst.PARAM_SIP_ID);
                List<SipMediaServer> sipMediaServerList = redisService.getList(Const.REDIS_DB_CONF_INDEX
                        , CacheKey.SIP_MEDIA_SERVER, SipMediaServer.class);
                for (SipMediaServer server : sipMediaServerList) {
                    if (sipId.equals(server.getSipId())) {
                        sipMediaServer = server;
                        break;
                    }
                }
            } catch (Exception e) {
                amiActionResponse.setMsg("invalid channel uniqueId");
                logger.error("ActionServiceHelper get sipMeidiaServerId error, ", e);
                return null;
            }
        }

        //取到了sipMediaServer, 正常都应该取到
        if (sipMediaServer != null) {
            ReferenceConfig<AmiActionService> referenceConfig = services.get(sipMediaServer.getIpAddr());
            if (referenceConfig == null) {
                referenceConfig = new ReferenceConfig<>();
                referenceConfig.setApplication(application);
                referenceConfig.setTimeout(APPLICATION_TIMEOUT);
                referenceConfig.setInterface(AmiActionService.class);
                referenceConfig.setUrl("dubbo://" + sipMediaServer.getIpAddr() + ":" + APPLICATION_PORT + "/com.tinet.ctilink.ami.action.AmiActionService");
                referenceConfig.setVersion(APPLICATION_VERSION);
                services.put(sipMediaServer.getIpAddr(), referenceConfig);
            }
            return referenceConfig.get();
        }

        return null;
    }

    public static AmiActionResponse validateRequest() {
        //白名单控制
        SystemSetting whiteIpSetting = redisService.get(Const.REDIS_DB_CONF_INDEX, String.format(CacheKey.SYSTEM_SETTING_NAME
                , SystemSettingConst.SYSTEM_SETTING_NAME_CONTROL_API_WHITE_IP_LIST), SystemSetting.class);
        if (whiteIpSetting != null) {
            String whiteIp = whiteIpSetting.getValue();
            if (com.alibaba.dubbo.common.utils.StringUtils.isNotEmpty(whiteIp)) {
                String clientIp = RpcContext.getContext().getRemoteHost();
                if (!AuthenticUtil.isInWhiteIpList(clientIp, whiteIp.split(","))) {
                    return new AmiActionResponse(-1, "invalid client ip");
                }
            }
        }
        //查询频度配置
        SystemSetting requestSetting = redisService.get(Const.REDIS_DB_CONF_INDEX, String.format(CacheKey.SYSTEM_SETTING_NAME
                , SystemSettingConst.SYSTEM_SETTING_NAME_CONTROL_API_MAX_REQUEST_COUNT), SystemSetting.class);
        //频度控制
        if (requestSetting != null) {
            //限制个数
            int limit = Integer.parseInt(requestSetting.getValue());
            //单位
            String property = requestSetting.getProperty();
            if (!AuthenticUtil.validateFrequency(redisService, CacheKey.CONTROL_API_REQUEST_COUNT, property, limit)) {
                return new AmiActionResponse(-1, "request is too frequently");
            }
        }
        return null;
    }
}
