package com.tinet.ctilink.control.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 15:47
 */
public class ActionRequest implements Serializable {
    private String action;
    private Map<String, String> params;

    public ActionRequest () {

    }
    public ActionRequest(String action, Map<String, String> params) {
        this.action = action;
        this.params = params;
    }

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
