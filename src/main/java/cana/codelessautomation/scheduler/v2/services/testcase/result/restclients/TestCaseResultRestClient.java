package cana.codelessautomation.scheduler.v2.services.testcase.result.restclients;


import cana.codelessautomation.scheduler.v2.services.testcase.result.models.UpdateTestCaseResultModel;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import services.restclients.commons.ErrorMessageModel;
import cana.codelessautomation.scheduler.v2.services.testcase.result.models.TestCaseResultModel;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;

@Path("/api")
@RegisterRestClient
public interface TestCaseResultRestClient {
    @PUT
    @Path("/testPlanResults/{testPlanResultId}/testCaseResults/{testCaseResultId}/status")
    List<ErrorMessageModel> updateTestCaseResultStatus(@Valid @PathParam Long testPlanResultId,
                                                       @Valid @PathParam Long testCaseResultId,
                                                       @Valid UpdateTestCaseResultModel updateTestCaseResultAsCompletedModel);

    @GET
    @Path("/testPlanResults/{testPlanResultId}")
    List<TestCaseResultModel> getTestCaseResultByPlanResultId(@Valid @PathParam Long testPlanResultId);
}
