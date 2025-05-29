package de.papenhagen;

import de.papenhagen.service.PointsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/pigmess")
public class PointCalculatingResource {

    private static final Logger log = LoggerFactory.getLogger(PointCalculatingResource.class);

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

