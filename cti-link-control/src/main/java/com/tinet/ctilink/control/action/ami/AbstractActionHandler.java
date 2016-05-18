package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.ami.action.AmiActionResponse;
import com.tinet.ctilink.ami.action.AmiActionService;
import com.tinet.ctilink.control.action.ActionHandler;
import com.tinet.ctilink.control.entity.ControlActionResponse;
import com.tinet.ctilink.control.util.ControlUtil;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/5/10 14:05
 */
public abstract class AbstractActionHandler implements ActionHandler {

    @Override
    public ControlActionResponse handle(Map<String, String> params) {
        ControlActionResponse controlActionResponse = new ControlActionResponse(-1, "service unavailable");
        AmiActionService amiActionService = ActionHandlerHelper.getService(getAction(), params, controlActionResponse);
        if (amiActionService != null) {
            AmiActionResponse amiActionResponse = amiActionService.handleAction(getAction(), params);
            return ControlUtil.toActionResponse(amiActionResponse);
        } else {
            return controlActionResponse;
        }

    }
}
