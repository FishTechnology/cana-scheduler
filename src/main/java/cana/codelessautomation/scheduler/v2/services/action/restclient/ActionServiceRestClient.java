package cana.codelessautomation.scheduler.v2.services.action.restclient;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/api")
@RegisterRestClient
public interface ActionServiceRestClient {
    @GET
    @Path("testCases/{testCaseId}/actions")
    List<ActionDetailModel> getActionsByTestCaseId(@PathParam Long testCaseId);
}
