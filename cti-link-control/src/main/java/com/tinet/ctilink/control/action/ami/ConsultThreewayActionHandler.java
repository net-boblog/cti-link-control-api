package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.inc.ControlAction;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/4/28 11:47
 */
@Component
public class ConsultThreewayActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return ControlAction.CONSULT_THREEWAY;
    }
}
