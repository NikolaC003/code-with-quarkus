package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Path;

@RegisterRestClient(configKey = "time-api")
public interface TimeApiClient {
    @GET
    @Path("/ip")
    @Produces(MediaType.APPLICATION_JSON)
    TimezoneResponse getTimezoneByIp(@QueryParam("ipAddress") String ip);
}
