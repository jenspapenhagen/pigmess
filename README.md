# pigmess
Online Calucator for the game of dice "Schweinerei"

The english Wikipedia:
https://en.wikipedia.org/wiki/Pass_the_Pigs

![Plastikschweine, die als Würfel benutzt werden.](/images/Pig_dice.jpg?raw=true "Plastikschweine, die als Würfel benutzt werden.")
by Arjan Verweij - http://membres.lycos.fr/arjan/

NEW DATASET:
<br>DOI: [10.1080/10691898.2006.11910593](https://doi.org/10.1080/10691898.2006.11910593)


OLD DATASET:
<br>Es wurde zur Berechnung der Wahrscheinlichkeiten auf die Datengrundlage von:
„Schweinereien“ – Grundschüler untersuchen einen asymmetrischen Zufallsgenerator
SANDRA SCHNABEL, MARBURG & BERND NEUBERT, GIESSEN
zugegriffen.
![Screenshot](/images/schweinewahrscheinlichkeit.png?raw=true "Screenshot aus dem Paper.")



Zum Starten:

```bash
./mvnw compile quarkus:dev
wget --header="Content-Type: text/json" http://localhost:8080/pigmess
```

For getting this Response
```json
{
 "points" : 20
}
```
