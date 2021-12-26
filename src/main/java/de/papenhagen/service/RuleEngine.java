package de.papenhagen.service;

import de.papenhagen.entities.Point;
import de.papenhagen.entities.Range;

import java.util.*;

import static de.papenhagen.entities.Postion.*;

public class RuleEngine {

    private RuleEngine() {
    }

    final static Point feedDown = new Point(5, FEEDS_DOWN, 9.05);
    final static Point feedUp = new Point(5, FEEDS_UP, 32.65);

    //stand on Nose is a combination of standing on nose and half nose standing with 0,20%
    final static Point standOnNose = new Point(10, STAND_ON_NOSE, 2.10 + 0.20);

    final static Point layLeft = new Point(1, LAY_LEFT, 31.05);
    final static Point layRight = new Point(1, LAY_RIGHT, 24.95);

    public static Optional<Point> getPointTo(final double number) {
        if (number < 0 || number > 100) {
            return Optional.empty();
        }
        Map<Range, Point> rangesMap = new HashMap<>();
        rangesMap.put(new Range(0, feedDown.getProbability()), feedDown);
        rangesMap.put(new Range(feedDown.getProbability() + 0.01, feedUp.getProbability()), feedUp);
        rangesMap.put(new Range(feedUp.getProbability() + 0.01, standOnNose.getProbability()), standOnNose);
        rangesMap.put(new Range(standOnNose.getProbability() + 0.01, layLeft.getProbability()), layLeft);
        rangesMap.put(new Range(layLeft.getProbability() + 0.01, layRight.getProbability()), layRight);

        return rangesMap.entrySet().stream()
                .filter(k -> k.getKey().contains(number))
                .map(Map.Entry::getValue)
                .findFirst();
    }

}
