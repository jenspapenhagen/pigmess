package de.papenhagen.service;

import de.papenhagen.entities.Position;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;

import java.util.Random;

@RequestScoped
public class PointsService {

    private Position firstRoll;

    private Position secondRoll;

    @PostConstruct
    private void roll() {
        final Random random = new Random();

        firstRoll =  RuleSetUtil.firstRoll(random.nextInt(100_000));
        secondRoll = RuleSetUtil.secondRoll(random.nextInt(100_000));
    }

    /**
     * This Methode calculate the Points on hand of 2 rolls a pig.
     *
     * @return the points
     */
    public int calculatePoints() {
        // extra Points for a dice cast with the same position of the pigs
        if (firstRoll.equals(secondRoll)) {
            return switch (firstRoll) {
                case FEEDS_UP, FEEDS_DOWN -> 20;
                case STAND_ON_NOSE -> 40;
                case STAND_HALF_ON_NOSE -> 60;
                case LAY_LEFT, LAY_RIGHT -> 1;
            };
        }

        // Wikipedia:
        // https://de.wikipedia.org/wiki/Schweinerei_(Spiel)#Bewertung
        final int completedRoll = firstRoll.getPoints() + secondRoll.getPoints();
        if (completedRoll % 5 == 0) {
            return completedRoll;
        }

        return Math.abs(completedRoll / 5);
    }


}
