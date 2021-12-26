package de.papenhagen.entities;

public class Range {
    private final double low;
    private final double high;

    public Range(double low, double high){
        this.low = low;
        this.high = high;
    }

    public boolean contains(double number){
        return (number >= low && number <= high);
    }
}
