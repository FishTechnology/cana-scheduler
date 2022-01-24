package cana.codelessautomation.scheduler.v2.services.scheduler.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import services.restclients.commons.ErrorMessageModel;
import services.restclients.schedule.models.ScheduleModel;
import services.restclients.schedule.models.UpdateScheduleStatusModel;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;

@Path("/api")
@RegisterRestClient
public interface ScheduleServiceRestClient {

    @GET
    @Path("/schedules/runningSchedule")
    List<ScheduleModel> getRunningSchedules();

    @GET
    @Path("/schedules/scheduleToExecute")
    ScheduleModel getScheduleToExecute();

    @PUT
    @Path("/schedules/{scheduleId}/status")
    List<ErrorMessageModel> updateScheduleStatus(@Valid @PathParam Long scheduleId, @Valid UpdateScheduleStatusModel updateScheduleStatusModel);
}
