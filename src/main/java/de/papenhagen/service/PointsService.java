package de.papenhagen.service;

import de.papenhagen.entities.Point;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Random;

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
        ValueRange rangeFeedDown = ValueRange.of(0, 905);
        ValueRange rangeFeedUp = ValueRange.of(906, 4171);

        ValueRange rangeStandOnNose = ValueRange.of(4172, 4382);
        ValueRange rangeStandHalfHalsNose = ValueRange.of(4383, 4403);

        ValueRange rangeLayLeft = ValueRange.of(4404, 7509);
        ValueRange rangeLayRight = ValueRange.of(7510, 10000);

        // build up the rule Set for this game
        final Point feedDown = new Point(5, FEEDS_DOWN, 9.05, rangeFeedDown);
        final Point feedUp = new Point(5, FEEDS_UP, 32.65, rangeFeedUp);

        final Point standOnNose = new Point(10, STAND_ON_NOSE, 2.10, rangeStandOnNose);
        final Point standHalfHalsNose = new Point(15, STAND_HALF_ON_NOSE, 0.20, rangeStandHalfHalsNose);

        final Point layLeft = new Point(1, LAY_LEFT, 31.05, rangeLayLeft);
        final Point layRight = new Point(1, LAY_RIGHT, 24.95, rangeLayRight);


        // the Rule set of this game
        final List<Point> pointList = List.of(feedDown, feedUp, standOnNose, standHalfHalsNose, layLeft, layRight);

        //roll
        Random random = new Random();
        final int randomNumber = random.nextInt(10000);

        firstRoll = pointList.stream()
                .filter(p -> p.getRange().isValidIntValue(randomNumber))
                .findFirst()
                .orElse(new Point(0, LAY_LEFT, 0, ValueRange.of(0, 0)));

        final int randomNumber2 = random.nextInt(100);
        secondRoll = pointList.stream()
                .filter(p -> p.getRange().isValidIntValue(randomNumber2))
                .findFirst()
                .orElse(new Point(0, LAY_LEFT, 0, ValueRange.of(0, 0)));
    }


}
