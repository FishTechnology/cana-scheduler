package services.restclients.result.testcaseresult;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import services.restclients.commons.ErrorMessageModel;
import services.restclients.result.testcaseresult.models.TestCaseResultModel;
import services.restclients.result.testcaseresult.models.UpdateTestCaseResultAsCompletedModel;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;

@Path("/api")
@RegisterRestClient
public interface TestCaseResultServiceRestClient {
    @GET
    @Path("testPlanResults/{testPlanResultId}")
    List<TestCaseResultModel> getTestCaseResultByPlanResultId(@Valid @PathParam Long testPlanResultId);

    @PUT
    @Path("testPlanResults/{testPlanResultId}/testCaseResults/{testCaseResultId}/status")
    List<ErrorMessageModel> updateTestCaseResultStatus(@Valid @PathParam Long testPlanResultId,
                                                       @Valid @PathParam Long testCaseResultId,
                                                       @Valid UpdateTestCaseResultAsCompletedModel updateTestCaseResultAsCompletedModel);
}
