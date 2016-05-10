package com.tinet.ctilink.control.entity;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 15:47
 */
public class ActionRequest {
    private String action;
    private Map<String, String> params;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
