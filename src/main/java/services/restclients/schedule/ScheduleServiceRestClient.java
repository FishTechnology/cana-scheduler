package services.restclients.schedule;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import services.restclients.schedule.models.ScheduleDetailModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/api")
@RegisterRestClient
public interface ScheduleServiceRestClient  {
    @GET
    @Path("/schedules/{scheduleId}/recurse")
    ScheduleDetailModel getAllSchedules(@PathParam Long scheduleId);
}
