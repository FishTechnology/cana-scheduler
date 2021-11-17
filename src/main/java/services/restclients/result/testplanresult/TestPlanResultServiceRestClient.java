package services.restclients.result.testplanresult;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import services.restclients.commons.ErrorMessageModel;
import services.restclients.result.testplanresult.models.TestPlanResultModel;
import services.restclients.result.testplanresult.models.UpdateTestPlanResultAsCompletedModel;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;

@Path("/api")
@RegisterRestClient
public interface TestPlanResultServiceRestClient {
    @GET
    @Path("scheduleIterationIds/{scheduleIterationId}")
    TestPlanResultModel getTestPlanResultBySchIterId(@Valid @PathParam Long scheduleIterationId);

    @PUT
    @Path("scheduleIterationIds/{scheduleIterationId}/testPlanResults/{testPlanResultId}/status")
    List<ErrorMessageModel> updateTestPlanResultStatus(@PathParam @Valid Long scheduleIterationId,
                                                       @PathParam @Valid Long testPlanResultId,
                                                       @Valid UpdateTestPlanResultAsCompletedModel updateTestPlanResultAsCompletedModel);
}

