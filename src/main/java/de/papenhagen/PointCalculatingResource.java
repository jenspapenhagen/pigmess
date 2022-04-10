package de.papenhagen;

import de.papenhagen.service.PointsService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pigmess")
@Slf4j
public class PointCalculatingResource {

    @Inject
    PointsService pointsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PointsResponse calculatePoints() {
        final int calculatePoints = pointsService.calculatePoints();
        log.info("points: {}", calculatePoints);

        return new PointsResponse(calculatePoints);
    }
}

