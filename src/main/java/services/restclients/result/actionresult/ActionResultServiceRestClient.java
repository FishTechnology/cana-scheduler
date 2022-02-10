package services.restclients.result.actionresult;

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
public interface ActionResultServiceRestClient {

    @POST
    @Path("testcaseResults/{testCaseResultId}/actionsResult/{actionResultId}")
    List<ErrorMessageModel> updateActionResult(
            @Valid @PathParam Long testCaseResultId,
            @Valid @PathParam Long actionResultId,
            @Valid UpdateActionResultModel updateActionResultModel);

    @GET
    @Path("testcaseResults/{testCaseResultId}/actionsResult")
    List<ActionResultModel> getActionResultsByTestCaseResultId(@Valid @PathParam Long testCaseResultId);

}
