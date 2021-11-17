package services.restclients.schedule;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import services.restclients.commons.ErrorMessageModel;
import services.restclients.schedule.models.ScheduleDetailModel;
import services.restclients.schedule.models.UpdateScheduleStatusModel;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;

@Path("/api")
@RegisterRestClient
public interface ScheduleServiceRestClient {
    @GET
    @Path("/schedules/{scheduleId}/recurse")
    ScheduleDetailModel getScheduler(@PathParam Long scheduleId);

    @PUT
    @Path("/schedules/{scheduleId}/status")
    List<ErrorMessageModel> updateScheduleStatus(@Valid @PathParam Long scheduleId,
                                                 @Valid UpdateScheduleStatusModel updateScheduleStatusModel);

    @POST
    @Path("/schedules/{scheduleId}/copy")
    List<ErrorMessageModel> copyTestPlanDetail(@Valid @PathParam Long scheduleId);
}
