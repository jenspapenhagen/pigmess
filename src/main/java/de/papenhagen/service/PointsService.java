package de.papenhagen.service;

import de.papenhagen.entities.Point;
import de.papenhagen.entities.Postion;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class PointsService {

    public int calculatePoints() {
        final double randomNumber = Math.random() * 100.0;
        final Optional<Point> pointOne = RuleEngine.getPointTo(randomNumber);
        final double randomNumber2 = Math.random() * 100.0;
        final Optional<Point> pointTwo = RuleEngine.getPointTo(randomNumber2);

        if (pointOne.isEmpty() || pointTwo.isEmpty()) {
            return 0;
        }

        final Point firstRoll = pointOne.get();
        final Point secondRoll = pointTwo.get();

        // Wikipedia:
        // https://de.wikipedia.org/wiki/Schweinerei_(Spiel)#Bewertung
        // Faule Sau – Beide Schweine liegen auf verschiedenen Seiten (einmal Punkt oben, einmal unten) – 0 Punkte
        // für aktuelle Runde und der nächste Spieler ist an der Reihe
        if (firstRoll.getCount() + secondRoll.getCount() == 2) {
            return 0;
        }

        //if both pigs have the same postion
        if (firstRoll.getPostion().equals(secondRoll.getPostion())) {
            final Postion firstRollPostion = firstRoll.getPostion();
            return switch (firstRollPostion) {
                case FEEDS_UP, FEEDS_DOWN -> 20;
                case STAND_ON_NOSE -> 40;
                case LAY_LEFT, LAY_RIGHT -> 1;
            };
        }

        return 0;
    }

}
