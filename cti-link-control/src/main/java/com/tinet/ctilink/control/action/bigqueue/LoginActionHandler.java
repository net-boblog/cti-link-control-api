package com.tinet.ctilink.control.action.bigqueue;

import com.tinet.ctilink.control.action.ActionHandler;
import com.tinet.ctilink.control.entity.Action;
import com.tinet.ctilink.control.entity.ActionResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 16:03
 */
@Component
public class LoginActionHandler implements ActionHandler {
    @Override
    public String getAction() {
        return Action.LOGIN;
    }

    @Override
    public ActionResponse handle(Map<String, String> params) {
        System.out.println("hello login");
        //调用big-queue接口登录
        return null;
    }
}
