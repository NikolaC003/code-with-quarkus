package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "eurorate-api")
public interface CurrencyApi {

    @GET
    @Path("/api/rates")
    @Produces(MediaType.APPLICATION_JSON)
    CurrencyResponse getExchangeRate(@QueryParam("from") String fromCurrency,@QueryParam("to") String toCurrency);
}
