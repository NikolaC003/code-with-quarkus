package org.acme;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/klijent")
public class KlijentResource {

    @Inject
    private KlijentService klijentService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addKlijent")
    public Response addKlijent(Klijent klijent) {
        try {
            klijentService.createKlijent(klijent);
        } catch (KlijentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllKlijenti")
    public Response getAllKlijenti() {
        List<Klijent> klijenti = null;
        try {
            klijenti = klijentService.getAllKlijenti();
        } catch (KlijentException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
        return Response.ok().entity(klijenti).build();
    }

    @GET
    @Path("/getKlijentByName")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKlijentByName(@QueryParam("name") String name) {
        List<Klijent> klijenti = klijentService.getKlijentByName(name);
        return Response.ok().entity(klijenti).build();
    }

    @GET
    @Path("/getAutomobiliByKlijentId")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAutomobiliByKlijentId(@QueryParam("id") Long id) {
        List<Automobil> automobili = klijentService.getAutomobiliByKlijentId(id);
        return Response.ok().entity(automobili).build();
    }
}