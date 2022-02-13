package cana.codelessautomation.scheduler.v2.services.action.result.option.resetclient;

import cana.codelessautomation.scheduler.v2.services.action.result.option.models.ActionOptionResultModel;
import cana.codelessautomation.scheduler.v2.services.action.result.option.models.UpdateActionOptionResultModel;
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
public interface ActionOptionResultRestClient {
    @GET
    @Path("/actionResults/{actionResultId}/actionOptionResults")
    List<ActionOptionResultModel> getActionOptionResultsByActionResultId(@Valid @PathParam Long actionResultId);

    @POST
    @Path("/actionResults/{actionResultId}/actionOptionResults/{actionOptionResultId}")
    List<ErrorMessageModel> updateActionOptionResult(
            @Valid @PathParam Long actionResultId,
            @Valid @PathParam Long actionOptionResultId,
            @Valid UpdateActionOptionResultModel updateActionOptionResultModel);
}
