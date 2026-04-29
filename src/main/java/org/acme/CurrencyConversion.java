package org.acme;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/currencyConversion")
public class CurrencyConversion {

    @Inject
    EntityManager em;

    @Inject
    @RestClient
    CurrencyApi currencyApi;

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response convertCurrency(@QueryParam("from") String from,@QueryParam("to") String to,
                @QueryParam("value") Double value,
                @QueryParam("userId") Long userId) {
        if (from == null || from.isBlank() || to == null || to.isBlank() || value == null || userId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query parametri 'from', 'to', 'value' i 'userId' su obavezni.")
                    .build();
        }

        Klijent klijent = em.find(Klijent.class, userId);
        if (klijent == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Klijent sa ID-jem " + userId + " nije pronadjen.")
                    .build();
        }

        try {
            CurrencyResponse currencyResponse = currencyApi.getExchangeRate(from, to);
            double rate = currencyResponse.resolveRate(to);

            currencyResponse.setCurrency(from);
            currencyResponse.setRate(rate);
            currencyResponse.setValue(value);
            currencyResponse.setConvertedValue(value * rate);

            klijent.valute.add(currencyResponse);
            em.merge(klijent);

            return Response.ok(currencyResponse).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Greska pri mijenjaju valute: " + e.getMessage())
                    .build();
        }
    }
}
