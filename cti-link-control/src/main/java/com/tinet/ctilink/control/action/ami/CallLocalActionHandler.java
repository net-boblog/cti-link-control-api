package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.Action;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/5/9 09:39
 */
@Component
public class CallLocalActionHandler extends AbstractActionHandler {

    @Override
    public String getAction() {
        return Action.CALL_LOCAL;
    }
}
