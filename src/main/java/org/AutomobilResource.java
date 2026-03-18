package org.acme;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/automobili")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutomobilResource {

    @Inject
    EntityManager entityManager;

    @GET
    public List<Automobil> dohvatiSve() {
        return entityManager.createNamedQuery(Automobil.GET_ALL_AUTOMOBILI, Automobil.class).getResultList();
    }

    @POST
    @Transactional
    public Response kreirajAuto(Automobil auto) {
        entityManager.persist(auto);
        return Response.status(Response.Status.CREATED).entity(auto).build();
    }
}