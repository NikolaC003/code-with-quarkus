package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/mehanicar")
public class MehanicarResource {

    @Inject
    private MehanicarService mehanicarService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addMehanicar")
    public Response addMehanicar(Mehanicar mehanicar) {
        try {
            mehanicarService.createMehanicar(mehanicar);
        } catch (MehanicarException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllMehanicari")
    public Response getAllMehanicari() {
        List<Mehanicar> mehanicari = null;
        try {
            mehanicari = mehanicarService.getAllMehanicari();
        } catch (MehanicarException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
        return Response.ok().entity(mehanicari).build();
    }

    @GET
    @Path("/getNaloziByMehanicarId")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNaloziByMehanicarId(@QueryParam("id") Long id) {
        List<RadniNalog> nalozi = mehanicarService.getNaloziByMehanicarId(id);
        return Response.ok().entity(nalozi).build();
    }
}