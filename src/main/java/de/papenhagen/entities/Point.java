package de.papenhagen.entities;

import java.time.temporal.ValueRange;

public record Point(int count, Postion postion, double probability, ValueRange range) {

}
