package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.exception.AutomobilException;
import me.fit.model.Automobil;
import me.fit.service.AutomobilService;
import java.util.List;

@Path("/automobil")
public class AutomobilResource {

    @Inject
    private AutomobilService automobilService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addAutomobil")
    public Response addAutomobil(Automobil auto) {
        try {
            automobilService.createAutomobil(auto);
        } catch (AutomobilException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllAutomobili")
    public Response getAllAutomobili() {
        List<Automobil> automobili = null;
        try {
            automobili = automobilService.getAllAutomobili();
        } catch (AutomobilException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
        return Response.ok().entity(automobili).build();
    }
}