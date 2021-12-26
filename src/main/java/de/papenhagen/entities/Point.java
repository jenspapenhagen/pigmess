package de.papenhagen.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {
    int count;
    Postion postion;
    double probability;
}
