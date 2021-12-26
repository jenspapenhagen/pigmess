package de.papenhagen.service;

import de.papenhagen.entities.Point;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PointsService {

    private Point firstRoll;

    private Point secondRoll;

    @PostConstruct
    private void roll() {
        final double randomNumber = Math.random() * 100.0;
        firstRoll = RuleEngine.getPointTo(randomNumber).orElseThrow();

        final double randomNumber2 = Math.random() * 100.0;
        secondRoll = RuleEngine.getPointTo(randomNumber2).orElseThrow();
    }

    /**
     * This Methode calulates the Points on hand of 2 rolls a pig.
     *
     * @return the points
     */
    public int calculatePoints() {
        // Wikipedia:
        // https://de.wikipedia.org/wiki/Schweinerei_(Spiel)#Bewertung
        // Faule Sau – Beide Schweine liegen auf verschiedenen Seiten (einmal Punkt oben, einmal unten) – 0 Punkte
        // für aktuelle Runde und der nächste Spieler ist an der Reihe
        if (firstRoll.getCount() + secondRoll.getCount() == 2) {
            return 0;
        }

        //if both pigs have the same postion
        if (firstRoll.getPostion().equals(secondRoll.getPostion())) {
            return switch (firstRoll.getPostion()) {
                case FEEDS_UP, FEEDS_DOWN -> 20;
                case STAND_ON_NOSE -> 40;
                case LAY_LEFT, LAY_RIGHT -> 1;
            };
        }

        return 0;
    }

}
