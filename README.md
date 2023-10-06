# pigmess
Online Calucator for the game of dice "Schweinerei"

The english Wikipedia:
https://en.wikipedia.org/wiki/Pass_the_Pigs

![Plastikschweine, die als Würfel benutzt werden.](/images/Pig_dice.jpg?raw=true "Plastikschweine, die als Würfel benutzt werden.")
by Arjan Verweij - http://membres.lycos.fr/arjan/

NEW DATASET:
<br>DOI: [10.1080/10691898.2006.11910593](https://doi.org/10.1080/10691898.2006.11910593)

<br>Raw frequencies

|Black Pig Position|(Dot Up)                       |(Dot Down)                |(Trotter)|(Razorback)|(Snouter)|(Leaning Jowler)|
|------------------|--------------------------------|---------------------------|----------|-----------|---------|----------------|
|**(Dot Up)**        |573                             |656                        |139       |360        |56       |12              |
|**(Dot Down)**      |623                             |731                        |185       |449        |58       |17              |
|**(Trotter)**       |155                             |180                        |45        |149        |17       |5               |
|**(Razorback)**     |396                             |473                        |124       |308        |45       |8               |
|**(Snouter)**       |54                              |67                         |13        |47         |2        |1               |
|**(Leaning Jowler)**|10                              |10                         |0         |7          |1        |1               |

(hint: these are only 5977 dice rolls, the missing result from 23 dice rolls are unknown)

relative frequencies

|Black Pig Position|(Dot Up)          |(Dot Down)        |(Trotter)         |(Razorback)       |(Snouter)          |(Leaning Jowler)   |
|----------|------------------|-------------------|-------------------|-------------------|--------------------|-------------------|
|**(Dot Up)**  |0,0955            |0,109333333333333  |0,0231666666666667 |0,06               |0,00933333333333333 |0,002              |
|**(Dot Down)** |0,103833333333333 |0,121833333333333  |0,0308333333333333 |0,0748333333333333 |0,00966666666666667 |0,00283333333333333 |
|**(Trotter)** |0,0258333333333333 |0,03               |0,0075             |0,0248333333333333 |0,00283333333333333 |0,000833333333333333|
|**(Razorback)** |0,066             |0,0788333333333333 |0,0206666666666667 |0,0513333333333333 |0,0075              |0,00133333333333333 |
|**(Snouter)** |0,009             |0,0111666666666667 |0,00216666666666667|0,00783333333333333|0,000333333333333333|0,000166666666666667|
|**(Leaning Jowler)**|0,00166666666666667|0,00166666666666667|0                  |0,00116666666666667|0,000166666666666667|0,000166666666666667|

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
