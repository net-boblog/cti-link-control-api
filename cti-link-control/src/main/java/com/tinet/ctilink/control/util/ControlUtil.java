package com.tinet.ctilink.control.util;

import com.tinet.ctilink.ami.action.AmiActionResponse;
import com.tinet.ctilink.control.entity.ActionResponse;

/**
 * @author fengwei //
 * @date 16/4/27 10:53
 */
public class ControlUtil {

    public static ActionResponse toActionResponse(AmiActionResponse response) {
        ActionResponse actionResponse = new ActionResponse();
        actionResponse.setCode(response.getCode());
        actionResponse.setMsg(response.getMsg());
        actionResponse.setValues(response.getValues());
        return actionResponse;
    }
}
