package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.inc.ControlAction;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/5/9 09:44
 */
@Component
public class BargeActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return ControlAction.BARGE;
    }
}
