package de.papenhagen.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.temporal.ValueRange;

@Data
@AllArgsConstructor
public class Point {
    int count;
    Postion postion;
    double probability;
    ValueRange range;
}
