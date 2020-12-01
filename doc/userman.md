# Felhasználói dokumentáció

[TOC]

## A program általános leírása

A program célja egy vírus terjedésének és lefolyásának szimulálása a lakosságon. A programban Pöttyök (Dot) jelképezi az egyes embereket. 4 féle Dot található a programban:

- Neutral(Semleges): A még meg nem fertőződött személyeket jelképezik
- Infectious(Fertőző): A már megfertőzött személyeket jelképezik
- Healthy(Egészséges): A már fertőző pöttyök képesek a gyógyulásra előre beállított eséllyel
- Dead(Halott): A már fertőző pöttyök képesek a meghalásra előre beállított eséllyel

A szimulációhoz egy téglalap tartozik. Ide lehet lehelyezni a pöttyöket. Ha két pötty ütközik egymással, akkor rugalmas ütközés megy végbe. Az ütközés jelképezi a kontaktálást. Ha egy semleges pötty fertőzöttel ütközik, akkor  az előre beállított esély alapján van esély a  megfertőződésre.

Egy fertőzött pötty másokat tovább tud fertőzni, illetve a lappangási idő letelte után (500 tick), lehetősége lesz gyógyulni, illetve meghalni a beállított valószínűség alapján.

## A program funkcionalitása a felhasználó szemszögéből

A program indítása után megnyílik a Simulation Editor (2. ábra) és betöltődik egy új üres szimuláció. Itt lehetőségünk van egy szimuláció előkészítésére. A programrész funciói:

- Szimuláció előkészítése
  - Szimuláció betöltése fájlból (kezdeti értékek) (File>Open simulation...)
  - Szimuláció mentése fájlba (kezdeti értékek) (File>Save simulation)
- Kezdeti értékek beállítása
  - Fertőzési esély (Infection Chance slider)
  - Halálozási esély (Mortality Chance slider)
  - Gyógyulási esély (Heal Chance slider)
  - Pötty sebessége(Speed slider)
  - n db pötty felhelyezése véletlenszerűen a pályára (Add button)
  - pöttyök egyesével történő felhelyezése a pályára, egér kattintás alapján (4db button)
  - A pálya kezdeti értékeinek törlése (Clear > Clear)
- Szimuláció elindítása
  - Kezdeti értékek alapján(Start > Start )

@image latex progSimEd.png

2.ábra - Szimuláció előkészítése.

A szimuláció előkészítéseként be kell állítanunk a csúszkák segítségével az esélyeket, majd pöttyöket kell hozzáadnunk a szimuláció sablonhoz. Pöttyöket kétféleképpen adhatunk hozzá. A legerdülő menüben kiválasztjuk a pötty típusát a mezőbe beírjuk a lerakandó pöttyök számát(Egész szám 0 és n között), majd rányomunk az Add gombra. A másik lehetőség, hogy először a 4 típust reprezentáló gombok egyikére kattintunk, majd a kívánt helyre mozgatva az egeret a bal egérgombbal kattintunk.

Ha elégedettek vagyunk a szimulálandó vírus sablonjával, akkor a sablont elmenthetjük a File>Save simulation segítségével.

A Start menüpont megnyitásával megnyillik egy új ablak, amiben a vírus szimulációja történik.  (3.ábra)

@image latex progSim.png

3.ábra - Vírus szimulálása

A programrész funkciói: 

- Szimuláció kezelése
  - Idő elindítása megállítása (Play/Pause)
  -  léptetés egyesével (Step)
  -  Idő gyorsítása (SpeedUp)
  -  Idő lassítása (SpeedDown)
  - statisztika megnyitása (Statistics)
- Szimuláció megjelenítése és lejátszása

A vírus terjedése körökre van osztva (Tick) a szimuláció sebessége nem befolyásolja ezt. Csupán a felhasználó számára telik két kör között gyorsabban vagy lassabban az idő.

A Statistics gomb megnyomása utána megjelenik egy új ablakban a szimulációhoz tartozó statisztika. (4.ábra)

@image latex progStat.png

Ha a szimuláció már el lett indítva 10 Tickenként a pillanatnyi állás elküldésre kerül a grafikonhoz. A grafikon 100 ms-enként frissül. Ha a statisztika bezárásra kerül, akkor az addig kapott adatok törlődnek.

Ha a grafikont nem nyitjuk, meg csak a sziumláció futása után, az adatok akkor is megjelennek.

3.ábra - Statisztika megjelenítése

A programrész funkciói: 

- Szimuláció statisztikája
  - Olyan diagram(tick szerint), ahol ábrázolva vannak a populáció, a fertőzöttek, a gyógyultak és a halottak

## A programban használt fájlkezelés

A szimuláció sablonjának fájba történő mentésére és onnan való betöltésére van lehetőség. Ha rossz fájlt nyitunk meg, akkor a standard outputon hibaüzenetet kapunk. A szimuláció sablonjának betöltésére alkalmas fájlokra a felhasználónak kell emlékeznie.