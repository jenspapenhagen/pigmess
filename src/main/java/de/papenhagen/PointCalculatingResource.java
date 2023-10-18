package de.papenhagen;

import de.papenhagen.service.PointsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;


@Path("/pigmess")
@Slf4j
public class PointCalculatingResource {

    @Inject
    PointsService pointsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PointsService.Output calculatePoints() {
        final PointsService.Output calculatePoints = pointsService.calculatePoints();
        log.info("points: {}", calculatePoints);

        return calculatePoints;
    }
}

