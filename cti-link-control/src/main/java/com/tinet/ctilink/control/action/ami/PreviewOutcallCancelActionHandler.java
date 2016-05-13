package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.Action;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/4/26 14:03
 */
@Component
public class PreviewOutcallCancelActionHandler extends AbstractActionHandler {

    @Override
    public String getAction() {
        return Action.PREVIEW_OUTCALL_CANCEL;
    }
}