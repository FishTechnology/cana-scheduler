package cana.codelessautomation.scheduler.v2.services.action.result.restclient;


import cana.codelessautomation.scheduler.v2.services.action.result.models.ActionResultModel;
import cana.codelessautomation.scheduler.v2.services.action.result.models.UpdateActionResultModel;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import services.restclients.commons.ErrorMessageModel;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@Path("/api")
@RegisterRestClient
public interface ActionResultRestClient {
    @GET
    @Path("testcaseResults/{testCaseResultId}/actionResults")
    List<ActionResultModel> getActionResultsByTestCaseResultId(@Valid @PathParam Long testCaseResultId);

    @POST
    @Path("testcaseResults/{testCaseResultId}/actionsResult/{actionResultId}")
    List<ErrorMessageModel> updateActionResult(
            @Valid @PathParam Long testCaseResultId,
            @Valid @PathParam Long actionResultId,
            @Valid UpdateActionResultModel updateActionResultModel);
}
