package de.papenhagen.entities;

import lombok.Getter;

@Getter
public enum Postion {
    FEEDS_DOWN(5, 9.05),
    FEEDS_UP(5, 32.65),
    STAND_ON_NOSE(10,2.10),
    STAND_HALF_ON_NOSE(15,0.20),
    LAY_LEFT(1,31.05),
    LAY_RIGHT(1,24.95);

    public final int points;
    public final double probability;

    Postion(int points, double probability) {
        this.points = points;
        this.probability = probability;
    }
}
