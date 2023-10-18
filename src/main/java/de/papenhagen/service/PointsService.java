package de.papenhagen.service;

import de.papenhagen.entities.DoubleRoll;
import de.papenhagen.entities.Position;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;

import java.security.SecureRandom;
import java.util.Random;

@RequestScoped
public class PointsService {

    private DoubleRoll roll;


    @PostConstruct
    private void roll() {
        final SecureRandom secureRandom = new SecureRandom();

        roll = RuleSetUtil.fullRoll(secureRandom.nextInt(100_000));
    }

    /**
     * This Methode calculate the Points on hand of 2 rolls a pig.
     *
     * @return the points
     */
    public Output calculatePoints() {
        // extra Points for a dice cast with the same position of the pigs
        if (roll.first().equals(roll.second())) {
            return switch (roll.first()) {
                case FEEDS_UP, FEEDS_DOWN -> new Output(roll.first(), roll.second(), 20);
                case STAND_ON_NOSE -> new Output(roll.first(), roll.second(), 40);
                case STAND_HALF_ON_NOSE -> new Output(roll.first(), roll.second(), 60);
                case LAY_LEFT, LAY_RIGHT -> new Output(roll.first(), roll.second(), 1);
            };
        }

        // Wikipedia:
        // https://de.wikipedia.org/wiki/Schweinerei_(Spiel)#Bewertung
        final int completedRoll = roll.first().getPoints() + roll.second().getPoints();
        if (completedRoll % 5 == 0) {
            return new Output(roll.first(), roll.second(), completedRoll);
        }

        return new Output(roll.first(), roll.second(), Math.abs(completedRoll / 5));
    }

    public record Output(Position rollFirst, Position rollSecond, int points) {

    }

    ;


}
