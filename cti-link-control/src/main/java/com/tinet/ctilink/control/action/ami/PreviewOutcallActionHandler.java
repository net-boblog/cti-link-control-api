package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.inc.ControlAction;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/4/26 13:50
 *
 * 外呼
 */
@Component
public class PreviewOutcallActionHandler extends AbstractActionHandler {

    @Override
    public String getAction() {
        return ControlAction.PREVIEW_OUTCALL;
    }
}
