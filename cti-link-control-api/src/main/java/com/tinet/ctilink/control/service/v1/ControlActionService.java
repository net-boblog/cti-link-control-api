package com.tinet.ctilink.control.service.v1;


import com.tinet.ctilink.ami.action.AmiActionResponse;
import com.tinet.ctilink.control.entity.ControlActionRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 15:42
 */
@Path("v1")
public interface ControlActionService {

    @POST
    AmiActionResponse handleAction(String action, Map<String, Object> params);

    /**
     * rest 调用
     * @param request 请求参数
     * @return
     */
    @POST
    @Path("/action")
    AmiActionResponse handleAction(ControlActionRequest request);
}
