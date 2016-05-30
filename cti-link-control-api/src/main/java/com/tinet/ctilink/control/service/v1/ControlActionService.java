package com.tinet.ctilink.control.service.v1;


import com.tinet.ctilink.ami.action.AmiActionResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/25 15:42
 */
@Path("v1/action")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ControlActionService {

    @POST
    AmiActionResponse handleAction(String action, Map<String, Object> params);
}
