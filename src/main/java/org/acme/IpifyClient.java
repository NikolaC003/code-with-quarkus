package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "ipify-api")
public interface IpifyClient {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String getPublicIp();
}
