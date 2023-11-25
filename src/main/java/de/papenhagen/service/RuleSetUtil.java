package de.papenhagen.service;

import de.papenhagen.entities.DoubleRoll;
import io.quarkus.logging.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static de.papenhagen.entities.Position.*;
import static java.util.Objects.nonNull;

public class RuleSetUtil {
    //dot up == LAY_LEFT
    //dot down == LAY_RIGHT
    //trotter == FEEDS_DOWN
    //razorback == FEEDS_UP
    //snouter ==  STAND_ON_NOSE
    //leaing juwler == STAND_HALF_ON_NOSE


    public static DoubleRoll fullRoll(final int randomNumber) {
        //Map Position/probability
        Map<DoubleRoll, Double> firstRoll = new HashMap<>();
        firstRoll.put(new DoubleRoll(LAY_LEFT, LAY_LEFT), calculatePercentage(573));
        firstRoll.put(new DoubleRoll(LAY_LEFT, LAY_RIGHT), calculatePercentage(623));
        firstRoll.put(new DoubleRoll(LAY_LEFT, FEEDS_DOWN), calculatePercentage(155));
        firstRoll.put(new DoubleRoll(LAY_LEFT, FEEDS_UP), calculatePercentage(396));
        firstRoll.put(new DoubleRoll(LAY_LEFT, STAND_ON_NOSE), calculatePercentage(54));
        firstRoll.put(new DoubleRoll(LAY_LEFT, STAND_HALF_ON_NOSE), calculatePercentage(10));

        firstRoll.put(new DoubleRoll(LAY_RIGHT, LAY_LEFT), calculatePercentage(656));
        firstRoll.put(new DoubleRoll(LAY_RIGHT, LAY_RIGHT), calculatePercentage(731));
        firstRoll.put(new DoubleRoll(LAY_RIGHT, FEEDS_DOWN), calculatePercentage(180));
        firstRoll.put(new DoubleRoll(LAY_RIGHT, FEEDS_UP), calculatePercentage(473));
        firstRoll.put(new DoubleRoll(LAY_RIGHT, STAND_ON_NOSE), calculatePercentage(67));
        firstRoll.put(new DoubleRoll(LAY_RIGHT, STAND_HALF_ON_NOSE), calculatePercentage(10));

        firstRoll.put(new DoubleRoll(FEEDS_DOWN, LAY_LEFT), calculatePercentage(139));
        firstRoll.put(new DoubleRoll(FEEDS_DOWN, LAY_RIGHT), calculatePercentage(185));
        firstRoll.put(new DoubleRoll(FEEDS_DOWN, FEEDS_DOWN), calculatePercentage(45));
        firstRoll.put(new DoubleRoll(FEEDS_DOWN, FEEDS_UP), calculatePercentage(124));
        firstRoll.put(new DoubleRoll(FEEDS_DOWN, STAND_ON_NOSE), calculatePercentage(13));
        firstRoll.put(new DoubleRoll(FEEDS_DOWN, STAND_HALF_ON_NOSE), calculatePercentage(0));

        firstRoll.put(new DoubleRoll(FEEDS_UP, LAY_LEFT), calculatePercentage(360));
        firstRoll.put(new DoubleRoll(FEEDS_UP, LAY_RIGHT), calculatePercentage(449));
        firstRoll.put(new DoubleRoll(FEEDS_UP, FEEDS_DOWN), calculatePercentage(149));
        firstRoll.put(new DoubleRoll(FEEDS_UP, FEEDS_UP), calculatePercentage(308));
        firstRoll.put(new DoubleRoll(FEEDS_UP, STAND_ON_NOSE), calculatePercentage(47));
        firstRoll.put(new DoubleRoll(FEEDS_UP, STAND_HALF_ON_NOSE), calculatePercentage(7));

        firstRoll.put(new DoubleRoll(STAND_ON_NOSE, LAY_LEFT), calculatePercentage(56));
        firstRoll.put(new DoubleRoll(STAND_ON_NOSE, LAY_RIGHT), calculatePercentage(58));
        firstRoll.put(new DoubleRoll(STAND_ON_NOSE, FEEDS_DOWN), calculatePercentage(17));
        firstRoll.put(new DoubleRoll(STAND_ON_NOSE, FEEDS_UP), calculatePercentage(46));
        firstRoll.put(new DoubleRoll(STAND_ON_NOSE, STAND_ON_NOSE), calculatePercentage(2));
        firstRoll.put(new DoubleRoll(STAND_ON_NOSE, STAND_HALF_ON_NOSE), calculatePercentage(1));

        firstRoll.put(new DoubleRoll(STAND_HALF_ON_NOSE, LAY_LEFT), calculatePercentage(12));
        firstRoll.put(new DoubleRoll(STAND_HALF_ON_NOSE, LAY_RIGHT), calculatePercentage(17));
        firstRoll.put(new DoubleRoll(STAND_HALF_ON_NOSE, FEEDS_DOWN), calculatePercentage(5));
        firstRoll.put(new DoubleRoll(STAND_HALF_ON_NOSE, FEEDS_UP), calculatePercentage(8));
        firstRoll.put(new DoubleRoll(STAND_HALF_ON_NOSE, STAND_ON_NOSE), calculatePercentage(1));
        firstRoll.put(new DoubleRoll(STAND_HALF_ON_NOSE, STAND_HALF_ON_NOSE), calculatePercentage(1));

        return getPosition(randomNumber, firstRoll);
    }

    private static Double calculatePercentage(final int number) {
        if (number <= 0) {
            return 0d;
        }
        return new BigDecimal(((number * 100) / 5977))
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue() * 1000;
    }


    private static DoubleRoll getPosition(final int randomNumber, final Map<DoubleRoll, Double> roll) {
        final long byte[] seed = getSecureRandomSeed();
        final SecureRandom random = = new SecureRandom(seed);
        final RandomSelector<DoubleRoll> selector = RandomSelector.weighted(roll.keySet(),s -> roll.get(s).doubleValue());
        final int elements = 100_000;
        final List<DoubleRoll> selection = new ArrayList<>(elements);
        if (nonNull(selector)) {
            for (int i = 0; i < elements; i++) {
                selection.add(selector.next(random));
            }
        }
        int size = selection.size();
        if (size <= randomNumber) {
            Log.error("randomNumber to high: " + randomNumber + " from: " + size);
            //fallback
            return selection.get(9000);
        }

        return selection.get(randomNumber);
    }

}
