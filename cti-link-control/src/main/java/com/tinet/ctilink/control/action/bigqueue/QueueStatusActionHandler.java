package com.tinet.ctilink.control.action.bigqueue;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tinet.ctilink.bigqueue.service.v1.AgentService;
import com.tinet.ctilink.control.action.ActionHandler;
import com.tinet.ctilink.control.entity.ActionConst;
import com.tinet.ctilink.control.entity.ActionResponse;
import com.tinet.ctilink.control.util.ControlUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/26 14:00
 */
@Component
public class QueueStatusActionHandler  implements ActionHandler {
    @Reference
    private AgentService agentService;

    @Override
    public String getAction() {
        return ActionConst.QUEUE_STATUS;
    }

    @Override
    public ActionResponse handle(Map<String, String> params) {
        com.tinet.ctilink.bigqueue.entity.ActionResponse actionResponse = agentService.queueStatus(params);
        return ControlUtil.toActionResponse(actionResponse);
    }
}
