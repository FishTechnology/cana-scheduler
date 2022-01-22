package cana.codelessautomation.scheduler.v2.services.config.restclient;

import cana.codelessautomation.scheduler.v2.services.config.models.ConfigModel;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/api/applications/{applicationId}")
@RegisterRestClient
public interface ConfigServiceRestClient {

    @GET
    @Path("/configs")
    List<ConfigModel> getConfigsByAppId(@Valid @PathParam Long applicationId);
}
