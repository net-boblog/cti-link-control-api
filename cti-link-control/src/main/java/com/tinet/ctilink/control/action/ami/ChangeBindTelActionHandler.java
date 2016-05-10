package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.Action;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/26 13:59
 */
@Component
public class ChangeBindTelActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return Action.CHANGE_BIND_TEL;
    }

}
