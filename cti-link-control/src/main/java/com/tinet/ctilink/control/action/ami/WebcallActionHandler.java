package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.inc.ControlAction;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/4/27 10:58
 */
@Component
public class WebcallActionHandler extends AbstractActionHandler {

    @Override
    public String getAction() {
        return ControlAction.WEBCALL;
    }
}
