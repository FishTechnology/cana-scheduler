package cana.codelessautomation.scheduler.v2.services.scheduler.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import services.restclients.schedule.models.ScheduleModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/api")
@RegisterRestClient
public interface ScheduleServiceRestClient {

    @GET
    @Path("/schedules/runningSchedule")
    ScheduleModel getRunningSchedule();

    @GET
    @Path("/schedules/scheduleToExecute")
    ScheduleModel getScheduleToExecute();
}
