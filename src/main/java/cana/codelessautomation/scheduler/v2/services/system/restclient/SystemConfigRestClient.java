package cana.codelessautomation.scheduler.v2.services.system.restclient;

import cana.codelessautomation.scheduler.v2.services.system.models.GetSystemConfigsByAppIdModel;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/api")
@RegisterRestClient
public interface SystemConfigRestClient {
    @GET
    @Path("/systemConfigs")
    GetSystemConfigsByAppIdModel getSystemConfigs();
}
