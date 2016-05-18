package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.inc.ControlAction;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/4/27 11:01
 */
@Component
public class HoldActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return ControlAction.HOLD;
    }
}
