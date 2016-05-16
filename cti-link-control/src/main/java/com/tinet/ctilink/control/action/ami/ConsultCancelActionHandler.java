package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.ActionConst;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/4/28 11:46
 */
@Component
public class ConsultCancelActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return ActionConst.CONSULT_CANCEL;
    }
}
