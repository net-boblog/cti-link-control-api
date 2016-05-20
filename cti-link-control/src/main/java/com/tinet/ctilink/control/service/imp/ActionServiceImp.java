package com.tinet.ctilink.control.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.tinet.ctilink.ami.action.AmiActionResponse;
import com.tinet.ctilink.ami.action.AmiActionService;
import com.tinet.ctilink.control.service.v1.ControlActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 15:58
 */
@Service
public class ActionServiceImp implements ControlActionService {
    private static Logger logger = LoggerFactory.getLogger(ActionServiceImp.class);

    @Override
    public AmiActionResponse handleAction(String action, Map<String, Object> params) {

        AmiActionResponse amiActionResponse = new AmiActionResponse(-1, "service unavailable");
        AmiActionService amiActionService = ActionHelper.getService(action, params, amiActionResponse);
        if (amiActionService != null) {
            return amiActionService.handleAction(action, null);
        } else {
            return amiActionResponse;
        }
    }
}
