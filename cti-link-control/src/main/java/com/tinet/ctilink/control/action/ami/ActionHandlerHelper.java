package com.tinet.ctilink.control.action.ami;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.tinet.ctilink.ami.action.AmiActionService;
import com.tinet.ctilink.bigqueue.entity.CallAgent;
import com.tinet.ctilink.cache.CacheKey;
import com.tinet.ctilink.cache.RedisService;
import com.tinet.ctilink.conf.model.SipGroup;
import com.tinet.ctilink.conf.model.SipMediaServer;
import com.tinet.ctilink.conf.model.Trunk;
import com.tinet.ctilink.control.entity.Action;
import com.tinet.ctilink.control.entity.ActionResponse;
import com.tinet.ctilink.inc.Const;
import com.tinet.ctilink.util.ContextUtil;
import com.tinet.ctilink.util.SipMediaServerUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/26 14:12
 *
 * 查询AmiActionService接口
 */

public class ActionHandlerHelper {
    private static Logger logger = LoggerFactory.getLogger(ActionHandlerHelper.class);
    private static final Map<String, ReferenceConfig<AmiActionService>> services = new HashMap<>();
    private static final ApplicationConfig application = new ApplicationConfig("cti-link-control-client");

    private static RedisService redisService;

    static {
        redisService = ContextUtil.getBean(RedisService.class);
    }

    //查询ami
    public static AmiActionService getService(String action, Map<String, String> params
            , ActionResponse actionResponse) {
        String enterpriseId = params.get("enterpriseId");
        String cno = params.get("cno");
        if (StringUtils.isNumeric(enterpriseId)) {
            actionResponse.setMsg("invalid enterpriseId");
            return null;
        }
        SipMediaServer sipMediaServer = null;
        if (action.equals(Action.PREVIEW_OUTCALL)
                || action.equals(Action.WEBCALL)
                || action.equals(Action.SELF_RECORD)
                || action.equals(Action.CALL_LOCAL)
                || action.equals(Action.DIRECT_CALL_START)) {  //不用区分在
            Trunk trunk = redisService.get(Const.REDIS_DB_CONF_INDEX, String.format(CacheKey.TRUNK_ENTERPRISE_ID_FIRST
                    , Integer.parseInt(enterpriseId)), Trunk.class);
            if (trunk == null) {
                actionResponse.setMsg("trunk first cache error");
                return null;
            }
            List<SipMediaServer> sipMediaServerList = redisService.getList(Const.REDIS_DB_CONF_INDEX
                    , CacheKey.SIP_MEDIA_SERVER, SipMediaServer.class);
            int sipGroupId = trunk.getSipGroupId();
            if (sipGroupId == -1) {
                List<SipGroup> sipGroupList = redisService.getList(Const.REDIS_DB_CONF_INDEX, CacheKey.SIP_GROUP
                        , SipGroup.class);
                //根据比例获取sipGroup
                int totalPercent = 0;
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
            List<SipMediaServer> targetList = new ArrayList<>();
            for (SipMediaServer server : sipMediaServerList) {
                if (server.getGroupId().equals(sipGroupId)) {
                    targetList.add(server);
                }
            }
            //随机获取一个sipMediaServer
            sipMediaServer = targetList.get(RandomUtils.nextInt(0, targetList.size()));

        } else {  //direct ip
            //TODO
            try {
                CallAgent callAgent = new CallAgent();
                //Sip-111-xxxxxxx
                String uniqueId = callAgent.getCurrentChannelUniqueId();
                if (StringUtils.isEmpty(uniqueId)) {
                    actionResponse.setMsg("channel uniqueId not exist");
                    return null;
                }
                Integer sipId = SipMediaServerUtil.getSipId(uniqueId);
                if (sipId == null) {
                    actionResponse.setMsg("channel uniqueId invalid");
                    return null;
                }

                List<SipMediaServer> sipMediaServerList = redisService.getList(Const.REDIS_DB_CONF_INDEX
                        , CacheKey.SIP_MEDIA_SERVER, SipMediaServer.class);
                for (SipMediaServer server : sipMediaServerList) {
                    if (sipId.equals(server.getSipId())) {
                        sipMediaServer = server;
                        break;
                    }
                }
            } catch (Exception e) {
                actionResponse.setMsg("invalid channel uniqueId");
                logger.error("ActionHandlerHelper get sipMeidiaServerId error, ", e);
                return null;
            }
        }

        //取到了sipMediaServer, 正常都应该取到
        if (sipMediaServer != null) {
            ReferenceConfig<AmiActionService> referenceConfig = services.get(sipMediaServer.getIpAddr());
            if (referenceConfig == null) {
                referenceConfig = new ReferenceConfig<>();
                referenceConfig.setApplication(application);
                referenceConfig.setTimeout(60000);
                referenceConfig.setInterface(AmiActionService.class);
                referenceConfig.setUrl("dubbo://" + sipMediaServer.getIpAddr() + "/com.tinet.ctilnk.ami.action.AmiActionService");
                referenceConfig.setVersion("0.0.1");
                services.put(sipMediaServer.getIpAddr(), referenceConfig);
            }
            return referenceConfig.get();
        }

        return null;
    }
}