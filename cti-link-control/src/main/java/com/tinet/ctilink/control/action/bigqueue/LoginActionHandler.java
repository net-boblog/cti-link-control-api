package com.tinet.ctilink.control.action.bigqueue;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tinet.ctilink.bigqueue.service.v1.AgentService;
import com.tinet.ctilink.control.action.ActionHandler;
import com.tinet.ctilink.control.entity.Action;
import com.tinet.ctilink.control.entity.ActionResponse;
import com.tinet.ctilink.control.util.ControlUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 16:03
 */
@Component
public class LoginActionHandler implements ActionHandler {
    @Reference
    private AgentService agentService;

    @Override
    public String getAction() {
        return Action.LOGIN;
    }

    @Override
    public ActionResponse handle(Map<String, String> params) {
        System.out.println("hello login");
        //调用big-queue接口登录
        com.tinet.ctilink.bigqueue.entity.ActionResponse actionResponse = agentService.login(params);
        return ControlUtil.toActionResponse(actionResponse);
    }
}
