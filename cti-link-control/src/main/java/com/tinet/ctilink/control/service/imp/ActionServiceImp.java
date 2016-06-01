package com.tinet.ctilink.control.service.imp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tinet.ctilink.ami.action.AmiActionResponse;
import com.tinet.ctilink.ami.action.AmiActionService;
import com.tinet.ctilink.ami.action.AmiBroadcastActionService;
import com.tinet.ctilink.control.entity.ControlActionRequest;
import com.tinet.ctilink.control.inc.ControlConst;
import com.tinet.ctilink.control.service.v1.ControlActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
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
        Date startTime = new Date();
        AmiActionResponse response = null;
        try {
            //安全验证
            response = ActionServiceHelper.validateRequest();
            if (response != null) {
                return response;
            }

            //广播
            if (params.containsKey(ControlConst.PARAM_CLUSTER)) {
                String cluster = params.get(ControlConst.PARAM_CLUSTER) == null ? "" : params.get(ControlConst.PARAM_CLUSTER).toString();
                if (cluster.equals(ControlConst.CLUSTER_TYPE_BROADCAST)) {
                    response = amiBroadcastActionService.handleAction(action, params);
                    return response;
                }
            }

            //单播, 寻址
            response = new AmiActionResponse(-1, "service unavailable");
            AmiActionService amiActionService = ActionServiceHelper.getService(params, response);
            if (amiActionService != null) {
                response = amiActionService.handleAction(action, params);
                return response;
            } else {
                return response;
            }
        } finally {
            Date endTime = new Date();
            if (logger.isInfoEnabled()) {
                logger.info("Request [action:" + action + ", params:" + params + "], Response [" + response + "]" +
                        ", Time [start:" + startTime.getTime() + ", end:" + endTime.getTime() + ", " + (endTime.getTime() - startTime.getTime()) + "ms]");
            }
        }
    }

    @Override
    public AmiActionResponse handleAction(ControlActionRequest request) {
        return handleAction(request.getAction(), request.getParams());
    }
}
