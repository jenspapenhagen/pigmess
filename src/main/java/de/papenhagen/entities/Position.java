package de.papenhagen.entities;

public enum Position {
    FEEDS_DOWN(5 ),
    FEEDS_UP(5 ),
    STAND_ON_NOSE(10),
    STAND_HALF_ON_NOSE(15),
    LAY_LEFT(1),
    LAY_RIGHT(1);

    public final int points;

    public int getPoints() {
        return points;
    }

    Position(int points) {
        this.points = points;
    }
}
