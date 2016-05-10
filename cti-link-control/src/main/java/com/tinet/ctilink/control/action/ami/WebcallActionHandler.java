package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.control.entity.Action;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/27 10:58
 */
@Component
public class WebcallActionHandler extends AbstractActionHandler {

    @Override
    public String getAction() {
        return Action.WEBCALL;
    }
}
