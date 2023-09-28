package de.papenhagen.service;

import de.papenhagen.entities.DiceCast;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;

import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Random;

import static de.papenhagen.entities.Postion.LAY_LEFT;

@RequestScoped
public class PointsService {

    private DiceCast firstRoll;

    private DiceCast secondRoll;

    @PostConstruct
    private void roll() {
        firstRoll = rollThePig();
        secondRoll =rollThePig();
    }

    /**
     * This Methode calculate the Points on hand of 2 rolls a pig.
     *
     * @return the points
     */
    public int calculatePoints() {
        // extra Points for a dice cast with the same position of the pigs
        if (firstRoll.postion().equals(secondRoll.postion())) {
            return switch (firstRoll.postion()) {
                case FEEDS_UP, FEEDS_DOWN -> 20;
                case STAND_ON_NOSE -> 40;
                case STAND_HALF_ON_NOSE -> 60;
                case LAY_LEFT, LAY_RIGHT -> 1;
            };
        }

        // Wikipedia:
        // https://de.wikipedia.org/wiki/Schweinerei_(Spiel)#Bewertung
        final int completedRoll = firstRoll.postion().getPoints() + secondRoll.postion().getPoints();
        if (completedRoll % 5 == 0) {
            return completedRoll;
        }

        return Math.abs(completedRoll / 5);
    }

    /**
     * pig roll with fallback on the most possible passion
     * @return the diceCast of the roll
     */
    private DiceCast rollThePig(){
        //the ruleset
        final List<DiceCast> ruleSet = RuleSetUtil.ruleSet();

        final Random random = new Random();
        final int randomNumber = random.nextInt(10000);

       return ruleSet.stream()
                .filter(diceCast -> diceCast.range().isValidIntValue(randomNumber))
                .findFirst()
                .orElse(new DiceCast(LAY_LEFT, ValueRange.of(0, 0)));

    }

}
