package com.tinet.ctilink.control.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.tinet.ctilink.control.action.ActionHandler;
import com.tinet.ctilink.control.entity.ControlActionRequest;
import com.tinet.ctilink.control.entity.ControlActionResponse;
import com.tinet.ctilink.control.service.v1.ControlActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 15:58
 */
@Service
public class ControlActionServiceImp implements ControlActionService {
    private static Logger logger = LoggerFactory.getLogger(ControlActionServiceImp.class);

    private Map<String, ActionHandler> handlerMap;

    @Autowired
    private List<ActionHandler> handlers;

    /**
     * 根据操作类型获取对应的处理器
     *
     * @param action
     * @return
     */
    private ActionHandler getHandler(String action) {
        if (handlerMap == null) {
            handlerMap = new HashMap<>();

            for (ActionHandler handler : handlers) {
                handlerMap.put(handler.getAction(), handler);
            }
        }

        ActionHandler handler = handlerMap.get(action);

        if (handler == null) {
            logger.error("ActionHandler for AmiAction: " + action + " not found.");
            throw new UnsupportedOperationException("ActionHandler for AmiAction: " + action + " not found.");
        }

        return handler;
    }

    @Override
    public ControlActionResponse handleAction(ControlActionRequest controlActionRequest) {
        logger.debug("AmiAction : {}", controlActionRequest);

        return this.getHandler(controlActionRequest.getAction()).handle(controlActionRequest.getParams());
    }
}
