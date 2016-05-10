package com.tinet.ctilink.control.action;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tinet.ctilink.ami.action.AmiActionService;
import com.tinet.ctilink.control.entity.ActionResponse;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 16:00
 */
public interface ActionHandler {

    String getAction();

    ActionResponse handle(Map<String, String> params);
}
