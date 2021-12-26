package de.papenhagen;

import de.papenhagen.service.PointsService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pigmess")
public class PointCalculatingResource {

    @Inject
    PointsService pointsService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculatePoints() {
        return pointsService.calculatePoints() + " Points";
    }
}