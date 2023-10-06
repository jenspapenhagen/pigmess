package de.papenhagen.service;

import de.papenhagen.entities.Position;

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

    public static Position firstRoll(final int position) {
        //Map Position/probability
        Map<Position, Double> firstRoll = new TreeMap<>();
        firstRoll.put(LAY_LEFT, 30.183);
        firstRoll.put(LAY_RIGHT, 35.283);
        firstRoll.put(FEEDS_DOWN, 8.433);
        firstRoll.put(FEEDS_UP, 22.0);
        firstRoll.put(STAND_ON_NOSE, 2.983);
        firstRoll.put(STAND_HALF_ON_NOSE, 0.733);

        return getPosition(position, firstRoll);
    }

    public static Position secondRoll(final int position) {
        //Map Position/probability
        Map<Position, Double> secondRoll = new TreeMap<>();
        secondRoll.put(LAY_LEFT, 29.933);
        secondRoll.put(LAY_RIGHT, 34.383);
        secondRoll.put(FEEDS_DOWN, 9.183);
        secondRoll.put(FEEDS_UP, 22.566);
        secondRoll.put(STAND_ON_NOSE, 3.066);
        secondRoll.put(STAND_HALF_ON_NOSE, 0.483);

        return getPosition(position, secondRoll);
    }

    private static Position getPosition(int position, Map<Position, Double> firstRoll) {
        final long randomSeed = 0;
        final Random random = new Random(randomSeed);
        final RandomSelector<Position> selector = RandomSelector.weighted(firstRoll.keySet(), firstRoll::get);
        final int elements = 100_000;
        final List<Position> selection = new ArrayList<>(elements);
        if (nonNull(selector)) {
            for (int i = 0; i < elements; i++) {
                selection.add(selector.next(random));
            }
        }

        return selection.get(position);
    }

}
