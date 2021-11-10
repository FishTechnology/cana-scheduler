package services.restclients.testplan;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import services.restclients.testplan.models.TestPlanModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/api")
@RegisterRestClient
public interface TestPlanServiceRestClient {
    @GET
    @PathParam("/testPlans/{testplanId}")
    TestPlanModel getTestPlanById(@PathParam Long testplanId);
}
