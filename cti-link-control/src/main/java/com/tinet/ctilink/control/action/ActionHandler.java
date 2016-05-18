package com.tinet.ctilink.control.action;

import com.tinet.ctilink.control.entity.ControlActionResponse;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 16:00
 */
public interface ActionHandler {

    String getAction();

    ControlActionResponse handle(Map<String, String> params);
}
