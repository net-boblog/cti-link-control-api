package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.ActionConst;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/5/9 09:49
 */
@Component
public class UnspyActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return ActionConst.UNSPY;
    }
}
