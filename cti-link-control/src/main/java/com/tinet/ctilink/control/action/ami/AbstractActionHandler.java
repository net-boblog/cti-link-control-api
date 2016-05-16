package com.tinet.ctilink.control.action.ami;

import com.tinet.ctilink.ami.action.AmiActionResponse;
import com.tinet.ctilink.ami.action.AmiActionService;
import com.tinet.ctilink.control.action.ActionHandler;
import com.tinet.ctilink.control.entity.ActionResponse;
import com.tinet.ctilink.control.util.ControlUtil;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/5/10 14:05
 */
public abstract class AbstractActionHandler implements ActionHandler {

    @Override
    public ActionResponse handle(Map<String, String> params) {
        ActionResponse actionResponse = new ActionResponse(-1, "service unavailable");
        AmiActionService amiActionService = ActionHandlerHelper.getService(getAction(), params, actionResponse);
        if (amiActionService != null) {
            AmiActionResponse amiActionResponse = amiActionService.handleAction(getAction(), params);
            return ControlUtil.toActionResponse(amiActionResponse);
        } else {
            return actionResponse;
        }

    }
}
