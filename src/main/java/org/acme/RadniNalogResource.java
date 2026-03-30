package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.exception.RadniNalogException;
import me.fit.model.RadniNalog;
import me.fit.service.RadniNalogService;
import java.util.List;

@Path("/radniNalog")
public class RadniNalogResource {

    @Inject
    private RadniNalogService nalogService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addNalog")
    public Response addNalog(RadniNalog nalog) {
        try {
            nalogService.createRadniNalog(nalog);
        } catch (RadniNalogException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllNalozi")
    public Response getAllNalozi() {
        List<RadniNalog> nalozi = null;
        try {
            nalozi = nalogService.getAllNalozi();
        } catch (RadniNalogException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
        return Response.ok().entity(nalozi).build();
    }
}