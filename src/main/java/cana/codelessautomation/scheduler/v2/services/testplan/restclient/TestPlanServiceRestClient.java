package cana.codelessautomation.scheduler.v2.services.testplan.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import services.restclients.testplan.models.TestPlanModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/api/applications/{applicationId}")
@RegisterRestClient
public interface TestPlanServiceRestClient {
    @GET
    @Path("/testPlans/{testplanId}")
    TestPlanModel getTestPlanById(@PathParam Long applicationId, @PathParam Long testplanId);
}
