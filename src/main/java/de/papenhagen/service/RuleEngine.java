package de.papenhagen.service;

import de.papenhagen.entities.Point;
import de.papenhagen.entities.Range;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class RuleEngine {

    private RuleEngine() {
    }

    public static Optional<Point> getPointTo(final double number, final List<Point> pointList) {
        //check for in range of the probability
        if (number < 0 || number > 100) {
            return Optional.empty();
        }
        //check if all probability are together
        if (pointList.stream().mapToDouble(Point::getProbability).sum() != 100) {
            log.error("the Sum of all given Point probability did not match 100%");
            return Optional.empty();
        }

        // adding starting form 0 zero
        // and ending of 100 to given points, to create ranges
        List<Double> rangePoints = new ArrayList<>();
        rangePoints.add(0.0);
        pointList.stream().mapToDouble(Point::getProbability).forEach(rangePoints::add);
        rangePoints.add(100.0);

        //building Range List
        List<Range> rangeList = new ArrayList<>();
        for (int i = 0; i < rangePoints.size(); i++) {
            int nextI = i + 1;
            rangeList.add(new Range(rangePoints.get(i), rangePoints.get(nextI)));
        }
        //finding the given Point in the ranges
        final Optional<Range> first = rangeList.stream()
                .filter(k -> k.contains(number))
                .findFirst();
        if (first.isEmpty()) {
            log.error("given number not found");
            return Optional.empty();
        }

        final int indexInRangeList = rangeList.indexOf(first.get());
        return Optional.of(pointList.get(indexInRangeList));
    }

}
