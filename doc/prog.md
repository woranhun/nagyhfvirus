# Programozói dokumentáció kiegészítés

[TOC]

## A programban található osztályok

Az osztályok részletesebb leírása a forráskódban található javadoc commentben, illetve a mellékelt pdf-ben található.

@image latex UML.png

### simulator package

A szimulátor belső működéséhez szükséges osztályokat tartalmazza.

#### Simulation Player

Egy szimuláció lejátszásához szükseges adatokat és függvényeket tárolja.

#### Simulation Statistics Store

Puffer tároló a statisztikai adatok tárolására.

#### Simulation Template

Egy szimuláció előkészítéséhez szükséges adatokat tárolja.

### simulator components

A szimulátorban felhasználható alkotóelemeket tartalmazza.

#### Dot

Pötty, ami egy embert reprezentál a pályán.

#### dotTypes

Enum, ami a pöttyök típusait tárolja

#### Point

A pályán lévő dolgok helyzetét, illetve bizonyos esetekben az origó és a pont közé rajzolható vectort valósítja meg. Az osztály tartalmaz a pontokon és vectorokon végezhető műveleteket is.

#### SimulationMap

A pálya hátterét reprezentálja. Legfontosabb feladata a Canvas letörlése.

### Tests

A programhoz tartozó Junit teszteket tartalmazza a package.

#### Dot Test

A Dotokra megírt teszteket tartalmazza.

#### Point Test

A pontokra megírt tesztet tartalmazza.

#### Simulation Template Test

A Simulation Template-re megírt tesztet tartalmazza.

### UI

A grafikus megjelenítéssel kapcsolatos osztályokat tartalmazza. Az itt található osztályok mind javaFX-et használnak.

#### Main

A program belépési pontja.

#### Sim Editor Controller

Szimuláció sablonjának szerkesztéséhez szükséges függvényeket tartalmazza, amelyek a felhasználóval kommunikálnak.

#### Sim Statistics Controller

A szimuláció megjelenítéséért felel. Közvetlen kapcsolatban áll a felhasználóval.

#### Simulation Player Controller

A szimuláció lejátszásához szükséges függvényeket tárolja. Közvetlen kapcsolatban áll a felhasználóval.

## A programban található interfész

### Steppable

Ez az interfész valósítja meg az összes léptethető dolgot a játékban.

## Fordítási tudnivalók

A program futtatásához szükség van a megfelelő javafx SDK-ra (javafx-sdk-11.0.2) [link](https://gluonhq.com/products/javafx/ ). Illetve a teszteléshez Junit 4-re és a hozzá tartozó függőségegkre (hamcrest-core-1.3.jar)  A programhoz mellékeltem az eclipse projektfájlokat.

## Egyéb tudnivalók

A program forrásainak készítése közben javadoc kommentet alkalmaztam. Az ebből Doxygen segítségével készített pdf fájlt mellékeltem a projekthez.

A programot az inteliJ IDEA IDE-vel készítettem. Az ablakok létrehozásához SceneBuildert használtam.

