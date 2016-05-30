package com.tinet.ctilink.control.service.imp;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.tinet.ctilink.ami.action.AmiActionResponse;
import com.tinet.ctilink.ami.action.AmiActionService;
import com.tinet.ctilink.ami.action.AmiBroadcastActionService;
import com.tinet.ctilink.cache.CacheKey;
import com.tinet.ctilink.cache.RedisService;
import com.tinet.ctilink.conf.model.SystemSetting;
import com.tinet.ctilink.control.service.v1.ControlActionService;
import com.tinet.ctilink.inc.Const;
import com.tinet.ctilink.inc.SystemSettingConst;
import com.tinet.ctilink.util.AuthenticUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 15:58
 */
@Service
public class ControlActionServiceImp implements ControlActionService {
    private static Logger logger = LoggerFactory.getLogger(ControlActionServiceImp.class);
    private static final String CLUSTER = "cluster";
    private static final String BROADCAST = "broadcast";

    @Reference
    private AmiBroadcastActionService amiBroadcastActionService;

    @Autowired
    private RedisService redisService;

    @Override
    public AmiActionResponse handleAction(String action, Map<String, Object> params) {
        //验证
        AmiActionResponse response = validateRequest();
        if (response != null) {
            return response;
        }

        //广播
        if (params.containsKey(CLUSTER)) {
            String cluster = params.get(CLUSTER) == null ? "" : params.get(CLUSTER).toString();
            if (cluster.equals(BROADCAST)) {
                return amiBroadcastActionService.handleAction(action, params);
            }
        }

        //寻址
        AmiActionResponse amiActionResponse = new AmiActionResponse(-1, "service unavailable");
        AmiActionService amiActionService = ActionServiceHelper.getService(params, amiActionResponse);
        if (amiActionService != null) {
            return amiActionService.handleAction(action, params);
        } else {
            return amiActionResponse;
        }
    }

    private AmiActionResponse validateRequest() {
        //白名单控制
        SystemSetting whiteIpSetting = redisService.get(Const.REDIS_DB_CONF_INDEX, String.format(CacheKey.SYSTEM_SETTING_NAME
                , SystemSettingConst.SYSTEM_SETTING_NAME_CONTROL_API_WHITE_IP_LIST), SystemSetting.class);
        if (whiteIpSetting != null) {
            String whiteIp = whiteIpSetting.getValue();
            if (StringUtils.isNotEmpty(whiteIp)) {
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
