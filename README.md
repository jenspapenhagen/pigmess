# pigmess
Online Calucator for the game of dice "Schweinerei"

The english Wikipedia:
https://en.wikipedia.org/wiki/Pass_the_Pigs

![Plastikschweine, die als Würfel benutzt werden.](/images/Pig_dice.jpg?raw=true "Plastikschweine, die als Würfel benutzt werden.")

by Arjan Verweij - http://membres.lycos.fr/arjan/


Es wurde zur Berechnung der Wahrscheinlichkeiten auf die Datengrundlage von:
„Schweinereien“ – Grundschüler untersuchen einen asymmetrischen Zufallsgenerator
SANDRA SCHNABEL, MARBURG & BERND NEUBERT, GIESSEN
zugegriffen.
![Screenshot](/images/schweinewahrscheinlichkeit.png?raw=true "Screenshot aus dem Paper.")



Zum Starten:

```bash
./mvnw compile quarkus:dev
curl -w "\n" http://localhost:8080/pigmess
```
