package org.acme;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/klijent")
public class KlijentResource {

    @Inject
    EntityManager em;

    @Inject
    private KlijentService klijentService;

    @Inject
    @RestClient
    IpifyClient ipifyClient;

    @Inject
    @RestClient
    TimeApiClient timeApiClient;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addKlijent")           
    @RolesAllowed("admin") 
    @Transactional
    public Response addKlijent(Klijent klijent) {
        try {
            klijentService.createKlijent(klijent);
            return Response.status(Response.Status.CREATED).build();
        } catch (KlijentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/getTimezoneByIP")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getTimezoneByIP(@QueryParam("userId") Long userId) {
        if (userId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Query param 'userId' je obavezan.")
                           .build();
        }

        Klijent klijent = em.find(Klijent.class, userId); 
        
        if (klijent == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Klijent sa ID-jem " + userId + " nije pronađen.")
                           .build();
        }

        try {
            String publicIp = ipifyClient.getPublicIp();

            TimezoneResponse timeData = timeApiClient.getTimezoneByIp(publicIp);

            klijent.vremenskeZone.add(timeData);
            
            em.merge(klijent);
            
            return Response.ok(klijent).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Greška u komunikaciji sa API servisima: " + e.getMessage())
                           .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllKlijenti")
    public Response getAllKlijenti() {
        try {
            List<Klijent> klijenti = klijentService.getAllKlijenti();
            return Response.ok().entity(klijenti).build();
        } catch (KlijentException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
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
