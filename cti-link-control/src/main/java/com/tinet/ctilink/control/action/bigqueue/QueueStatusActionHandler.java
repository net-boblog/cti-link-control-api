package com.tinet.ctilink.control.action.bigqueue;

import com.tinet.ctilink.control.action.ActionHandler;
import com.tinet.ctilink.control.entity.Action;
import com.tinet.ctilink.control.entity.ActionResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/26 14:00
 */
@Component
public class QueueStatusActionHandler  implements ActionHandler {
    @Override
    public String getAction() {
        return Action.QUEUE_STATUS;
    }

    @Override
    public ActionResponse handle(Map<String, String> params) {
        return null;
    }
}
