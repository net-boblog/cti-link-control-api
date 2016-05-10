package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.Action;
import org.springframework.stereotype.Component;

import java.util.Map;

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
        return Action.PREVIEW_OUTCALL;
    }
}
