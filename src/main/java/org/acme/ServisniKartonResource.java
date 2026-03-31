package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/servisniKarton")
public class ServisniKartonResource {

    @Inject
    ServisniKartonService kartonService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addKarton")
    public Response addKarton(ServisniKarton karton) {
        try {
            kartonService.createKarton(karton);
        } catch (ServisniKartonException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllKartoni")
    public Response getAllKartoni() {
        List<ServisniKarton> kartoni = null;
        try {
            kartoni = kartonService.getAllKartoni();
        } catch (ServisniKartonException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
        return Response.ok().entity(kartoni).build();
    }
}