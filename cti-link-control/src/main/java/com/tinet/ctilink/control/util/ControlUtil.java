package com.tinet.ctilink.control.util;

import com.tinet.ctilink.ami.action.AmiActionResponse;
import com.tinet.ctilink.control.entity.ControlActionResponse;

/**
 * @author fengwei //
 * @date 16/4/27 10:53
 */
public class ControlUtil {

    public static ControlActionResponse toActionResponse(AmiActionResponse response) {
        if (response == null) {
            return null;
        }
        ControlActionResponse controlActionResponse = new ControlActionResponse();
        controlActionResponse.setCode(response.getCode());
        controlActionResponse.setMsg(response.getMsg());
        controlActionResponse.setValues(response.getValues());
        return controlActionResponse;
    }
}
