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
 * @date 16/4/26 13:53
 */
@Component
public class UnpauseActionHandler  implements ActionHandler {
    @Reference
    private AgentService agentService;

    @Override
    public String getAction() {
        return ActionConst.UNPAUSE;
    }

    @Override
    public ActionResponse handle(Map<String, String> params) {
        com.tinet.ctilink.bigqueue.entity.ActionResponse actionResponse = agentService.unpause(params);
        return ControlUtil.toActionResponse(actionResponse);
    }
}
