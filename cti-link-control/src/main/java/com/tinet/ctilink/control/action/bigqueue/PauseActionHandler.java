package com.tinet.ctilink.control.action.bigqueue;

import com.tinet.ctilink.control.action.ActionHandler;
import com.tinet.ctilink.control.entity.Action;
import com.tinet.ctilink.control.entity.ActionResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/26 13:50
 */
@Component
public class PauseActionHandler  implements ActionHandler {
    @Override
    public String getAction() {
        return Action.PAUSE;
    }

    @Override
    public ActionResponse handle(Map<String, String> params) {
        return null;
    }
}
