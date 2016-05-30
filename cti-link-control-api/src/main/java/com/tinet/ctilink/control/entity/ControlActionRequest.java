package com.tinet.ctilink.control.entity;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/5/30 20:49
 */
public class ControlActionRequest {
    private String action;

    private Map<String, Object> params;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
