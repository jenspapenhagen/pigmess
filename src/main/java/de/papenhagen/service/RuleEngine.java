package de.papenhagen.service;

import de.papenhagen.entities.Point;
import de.papenhagen.entities.Range;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        if (rangePoints.size() % 2 != 0){
            rangePoints.add(100.0);
        }
        //split the list into an odd and an even part
        int midIndex = (rangePoints.size() - 1) / 2;
        List<List<Double>> lists = new ArrayList<>(
                rangePoints.stream()
                        .collect(Collectors.partitioningBy(s -> rangePoints.indexOf(s) > midIndex))
                        .values()
        );

        final List<Double> doubleList = lists.get(0);
        final List<Double> doubleList2 = lists.get(1);
        if (doubleList.size() != doubleList2.size()) {
            log.error("the splitting has not worked correct.");
            return Optional.empty();
        }

        List<Range> rangeList = IntStream.range(0, (rangePoints.size() / 2 ) - 1)
                .mapToObj(i -> new Range(doubleList.get(i), doubleList2.get(i)))
                .collect(Collectors.toList());

        //finding the given Point in the ranges
        final Optional<Range> firstPointInRange = rangeList.stream()
                .filter(k -> k.contains(number))
                .findFirst();

        if (firstPointInRange.isEmpty()) {
            log.debug("given number not found");
            return Optional.of(pointList.get(0));
        }

        final int indexInRangeList = rangeList.indexOf(firstPointInRange.get());
        return Optional.of(pointList.get(indexInRangeList));
    }

}
