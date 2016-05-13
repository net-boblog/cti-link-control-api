package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.Action;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @date 16/5/9 09:55
 */
@Component
public class ChannelHangupActionHandler extends AbstractActionHandler {
    @Override
    public String getAction() {
        return Action.CHANNEL_HANGUP;
    }
}