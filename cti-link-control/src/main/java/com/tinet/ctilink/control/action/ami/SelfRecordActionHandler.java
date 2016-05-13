package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.Action;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/27 10:59
 */
@Component
public class SelfRecordActionHandler extends AbstractActionHandler {

    @Override
    public String getAction() {
        return Action.SELF_RECORD;
    }

}
