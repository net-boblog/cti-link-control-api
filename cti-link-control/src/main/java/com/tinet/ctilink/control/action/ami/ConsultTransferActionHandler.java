package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.ActionConst;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/4/28 11:47
 */
@Component
public class ConsultTransferActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return ActionConst.CONSULT_TRANSFER;
    }

}
