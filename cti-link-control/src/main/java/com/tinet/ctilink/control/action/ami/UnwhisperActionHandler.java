package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.ActionConst;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/5/9 09:51
 */
@Component
public class UnwhisperActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return ActionConst.UNWHISPER;
    }
}
