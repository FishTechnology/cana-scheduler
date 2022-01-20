package cana.codelessautomation.scheduler.v2.services.testcase.restclient;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import services.restclients.testcase.TestCaseModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/api/applications/{applicationId}")
@RegisterRestClient
public interface TestCaseServiceRestClient {
    @GET
    @Path("/testPlans/{testPlanId}/testCases")
    List<TestCaseModel> getTestCaseByTestPlanId(@PathParam Long applicationId,@PathParam Long testPlanId);
}
