package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.inc.ControlAction;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/5/9 09:45
 */
@Component
public class DisconnectActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return ControlAction.DISCONNECT;
    }
}
