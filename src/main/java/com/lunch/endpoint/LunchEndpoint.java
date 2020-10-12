package com.lunch.endpoint;

import com.lunch.service.LunchService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/lunch")
@Produces(MediaType.APPLICATION_JSON)
public class LunchEndpoint {

    private final LunchService lunchService;

    @Inject
    public LunchEndpoint(LunchService lunchService) {
        this.lunchService = lunchService;
    }

    @GET
    public Response get() {
        return Response.ok(lunchService.get()).build();
    }

}
