package com.tinet.ctilink.control.service.imp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tinet.ctilink.ami.action.AmiActionResponse;
import com.tinet.ctilink.ami.action.AmiActionService;
import com.tinet.ctilink.ami.action.AmiBroadcastActionService;
import com.tinet.ctilink.control.inc.ControlConst;
import com.tinet.ctilink.control.service.v1.ControlActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 15:58
 */
@Service
public class ActionServiceImp implements ControlActionService {
    private static Logger logger = LoggerFactory.getLogger(ActionServiceImp.class);

    @Reference
    private AmiBroadcastActionService amiBroadcastActionService;

    @Override
    public AmiActionResponse handleAction(String action, Map<String, Object> params) {
        //安全验证
        AmiActionResponse response = ActionServiceHelper.validateRequest();
        if (response != null) {
            return response;
        }

        //广播
        if (params.containsKey(ControlConst.PARAM_CLUSTER)) {
            String cluster = params.get(ControlConst.PARAM_CLUSTER) == null ? "" : params.get(ControlConst.PARAM_CLUSTER).toString();
            if (cluster.equals(ControlConst.CLUSTER_TYPE_BROADCAST)) {
                return amiBroadcastActionService.handleAction(action, params);
            }
        }

        //单播, 寻址
        AmiActionResponse amiActionResponse = new AmiActionResponse(-1, "service unavailable");
        AmiActionService amiActionService = ActionServiceHelper.getService(params, amiActionResponse);
        if (amiActionService != null) {
            return amiActionService.handleAction(action, params);
        } else {
            return amiActionResponse;
        }
    }
}
