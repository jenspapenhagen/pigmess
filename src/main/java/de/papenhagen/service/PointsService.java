package de.papenhagen.service;

import de.papenhagen.entities.Point;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import java.util.List;

import static de.papenhagen.entities.Postion.*;

@RequestScoped
public class PointsService {

    private Point firstRoll;

    private Point secondRoll;


    /**
     * This Methode calculate the Points on hand of 2 rolls a pig.
     *
     * @return the points
     */
    public int calculatePoints() {
        //if both pigs have the same position
        if (firstRoll.getPostion().equals(secondRoll.getPostion())) {
            return switch (firstRoll.getPostion()) {
                case FEEDS_UP, FEEDS_DOWN -> 20;
                case STAND_ON_NOSE -> 40;
                case STAND_HALF_ON_NOSE -> 60;
                case LAY_LEFT, LAY_RIGHT -> 1;
            };
        }

        // Wikipedia:
        // https://de.wikipedia.org/wiki/Schweinerei_(Spiel)#Bewertung
        final int completedRoll = firstRoll.getCount() + secondRoll.getCount();
        if (completedRoll % 5 == 0) {
            return completedRoll;
        }

        return Math.abs(completedRoll / 5);
    }

    @PostConstruct
    private void roll() {
        // build up the rule Set for this game
        final Point feedDown = new Point(5, FEEDS_DOWN, 9.05);
        final Point feedUp = new Point(5, FEEDS_UP, 32.65);

        final Point standOnNose = new Point(10, STAND_ON_NOSE, 2.10);
        final Point standHalfHalsNose = new Point(15, STAND_HALF_ON_NOSE, 0.2);

        final Point layLeft = new Point(1, LAY_LEFT, 31.05);
        final Point layRight = new Point(1, LAY_RIGHT, 24.95);

        // the Rule set of this game
        final List<Point> pointList = List.of(feedDown, feedUp, standOnNose, standHalfHalsNose, layLeft, layRight);

        //roll
        final double randomNumber = Math.random() * 100.0;
        firstRoll = RuleEngine.getPointTo(randomNumber, pointList).orElse(new Point(0, LAY_LEFT, 0));

        final double randomNumber2 = Math.random() * 100.0;
        secondRoll = RuleEngine.getPointTo(randomNumber2, pointList).orElse(new Point(0, LAY_LEFT, 0));
    }


}
