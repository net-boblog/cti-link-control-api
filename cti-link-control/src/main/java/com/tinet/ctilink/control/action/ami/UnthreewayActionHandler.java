package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.ActionConst;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/4/28 11:51
 */
@Component
public class UnthreewayActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return ActionConst.UNTHREEWAY;
    }
}
