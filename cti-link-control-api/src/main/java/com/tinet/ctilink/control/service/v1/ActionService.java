package com.tinet.ctilink.control.service.v1;

import com.tinet.ctilink.control.entity.ActionRequest;
import com.tinet.ctilink.control.entity.ActionResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author fengwei //
 * @date 16/4/25 15:42
 */
@Path("v1/action")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ActionService {

    @POST
    ActionResponse handleAction(ActionRequest actionRequest);

}
