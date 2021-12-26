package de.papenhagen.service;

import de.papenhagen.entities.Point;
import de.papenhagen.entities.RuleEngine;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class PointsService {

    public int calculatePoints() {
        final double randomNumber = Math.random() * 100.0;
        final Optional<Point> pointOne = RuleEngine.getPointTo(randomNumber);
        final double randomNumber2 = Math.random() * 100.0;
        final Optional<Point> pointTwo = RuleEngine.getPointTo(randomNumber2);

        if (pointOne.isEmpty() || pointTwo.isEmpty()) {
            return 0;
        }
        return pointOne.get().getCount() + pointTwo.get().getCount();

    }

}
