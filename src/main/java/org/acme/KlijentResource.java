package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/klijenti") // <-- OVO MORA BITI TU!
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KlijentResource {

    @Inject
    KlijentService klijentService;

    @GET
    public List<Klijent> list() {
        return klijentService.dohvatiSve();
    }

    @POST
    public Response create(Klijent klijent) {
        Klijent novi = klijentService.sacuvaj(klijent);
        return Response.status(201).entity(novi).build();
    }
}