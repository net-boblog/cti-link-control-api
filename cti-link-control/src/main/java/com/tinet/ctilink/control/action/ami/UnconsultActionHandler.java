package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.inc.ControlAction;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/4/28 11:48
 */
@Component
public class UnconsultActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return ControlAction.UNCONSULT;
    }
}
