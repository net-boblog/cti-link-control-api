package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.ActionConst;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/4/27 10:59
 */
@Component
public class SelfRecordActionHandler extends AbstractActionHandler {

    @Override
    public String getAction() {
        return ActionConst.SELF_RECORD;
    }

}
