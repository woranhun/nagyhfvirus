package simulator;

import javafx.scene.canvas.Canvas;
import simulatorComponents.Dot;
import simulatorComponents.SimulationMap;
import simulatorComponents.dotTypes;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * SimulationPlayer osztály.
 * Egy szimuláció lejátszásához szükséges adatokat és függvényeket tárolja.
 */
public class SimulationPlayer extends TimerTask {
    /**
     * Léptethető dolgok tárolója
     */
    static ArrayList<Steppable> stepables;
    /**
     * Olyan léptethető dolgok tárolója, amit el kell távolítani a steppables közül a Tick végén
     */
    static ArrayList<Steppable> removeInTheEnd;
    /**
     * Olyan léptethető dolgok tárolója, amit hozzá kell adni a léptethető dolgok közé a Tick végén
     */
    static ArrayList<Steppable> addInTheEnd;
    /**
     * Éppen fertőző Dotok száma
     */
    static int infectedCnt;
    /**
     * Éppen halott Dotok száma
     */
    static int deadCnt;
    /**
     * Éppen egészségese Dotok száma
     */
    static int healedCnt;
    /**
     * Éppen semleges Dotok száma
     */
    static int neutralCnt;
    /**
     * Egy Tick Ms-ban mérve
     */
    static int oneTickInMs;
    /**
     * Szimuláció Statisztikát tároló osztály
     * Pufferként funkcionál
     */
    SimulationStatisticsStore sss;
    /**
     * Két kör között eltelt minimális periódusidő
     */
    int minPeriod;
    /**
     * Időzítő, a körönként történő lépésért felel
     */
    Timer timer;
    /**
     * Le van-e szüneteltetve a szimuláció
     */
    boolean paused;
    /**
     * A canvas, amire rajzolunk
     */
    Canvas canvas;
    /**
     * Éppen aktuális kör sorszáma
     */
    int currTick;
    /**
     * Szimuláció kezdete óta eltelt idő
     */
    int millisecondsElapsed;
    /**
     * Adatküldés gyakorisága
     */
    int sendDataPeriod = 10;

    /**
     * SimulationPlayer konstruktora
     *
     * @param sim    A kapott SimulationTemplate
     * @param canvas A kapott Canvas
     * @param sss    A kapott statisztika tároló, ami pufferként funkcionál
     */
    public SimulationPlayer(SimulationTemplate sim, Canvas canvas, SimulationStatisticsStore sss) {
        deadCnt = 0;
        healedCnt = 0;
        neutralCnt = 0;
        infectedCnt = 0;
        currTick = 0;
        millisecondsElapsed = 0;
        minPeriod = 10;
        oneTickInMs = 8;

        this.sss = sss;
        this.canvas = canvas;
        stepables = new ArrayList<>();
        stepables.add(new SimulationMap(canvas));
        for (Dot st : sim.getDots()) {
            stepables.add(st);
            st.init(canvas);

            if (st.getType() == dotTypes.Infectious) {
                infectedCnt++;
            } else if (st.getType() == dotTypes.Healthy) {
                healedCnt++;
            } else if (st.getType() == dotTypes.Dead) {
                deadCnt++;
            } else {
                neutralCnt++;
            }
        }
        timer = new Timer(true);
        timer.schedule(this, 0, minPeriod);
        paused = true;
        removeInTheEnd = new ArrayList<>();
        addInTheEnd = new ArrayList<>();
    }

    /**
     * Lappangási időt visszadó függvény
     *
     * @return Lappangási idő Tickekben mérve
     */
    public static int getIncubationPeriod() {
        return 500;
    }

    /**
     * Halott Dot eltűnési idejét határozza meg
     *
     * @return Eltűnési idő Tickekben mérve
     */
    public static int getRemoveTime() {
        return 150;
    }

    /**
     * Hozzáadja a Steppable dolgot az eltávolítandók listájához
     *
     * @param st Az eltávolítandó Steppable
     */
    static public void removeSteppable(Steppable st) {
        removeInTheEnd.add(st);
    }

    /**
     * Hozzáadja a Steppable dolgot a hozzáadandóak listájához
     *
     * @param st A hozzáadandó Steppable
     */
    static public void addSteppable(Steppable st) {
        addInTheEnd.add(st);
    }

    /**
     * Viszzadja az eltávolítandóak listáját
     *
     * @return Az eltávolítandóak listája
     */
    public static ArrayList<Steppable> getRemove() {
        return removeInTheEnd;
    }

    /**
     * Új fertőzött esetén növeli a fertőzötteket és csökkenti a semleges Dotok számát
     */
    public static void addInfectedDot() {
        infectedCnt++;
        neutralCnt--;
    }

    /**
     * Új gyógyult esetén növeli a gyógyultakat és csökkenti a fertőző Dotok számát
     */
    public static void addHealedDot() {
        healedCnt++;
        infectedCnt--;
    }

    /**
     * Új fertőzött esetén növeli a halottak és csökkenti a fertőző Dotok számát
     */
    public static void addDeadDot() {
        deadCnt++;
        infectedCnt--;
    }

    /**
     * A Canvasról kilógó dotokat visszarakja a Canvas-ra
     *
     * @param img A kapott Canvas
     */
    public void moveDotsFromOutOfWindow(Canvas img) {
        for (Steppable s : stepables) {
            if (s.isOutOfWindow(canvas))
                s.moveBack(img);
        }
    }

    /**
     * Frissíti a teljes szimuláció tartalmát
     * Az első Steppable mindig a pálya maga
     *
     * @param c A kapott Canvas
     */
    public void refresh(Canvas c) {
        for (Steppable s : stepables) {
            s.refresh(c);
        }
    }

    /**
     * Timer hívja minPeriod időközönként.
     * Ha nincs szüneteltetve, akkor oneTickInMs ms-ként megtesz egy lépést
     */
    @Override
    public void run() {
        if (!paused) {
            if (millisecondsElapsed % oneTickInMs == 0) {
                forwardOneStep();
            } else {
                System.gc();
            }
            millisecondsElapsed += minPeriod;
        }
    }

    /**
     * negálja a paused változó értékét
     */
    public void changePlayAndPause() {
        paused = !paused;
    }

    /**
     * Egy lépést szimulál le.
     * Először mindenki lép. Utána az ütközések jönnek, majd eltávolítjuk és hozzáadjuk a szükséges Steppable-öket,
     * majd Frissítjük a Canvast. Végül a jelentlegi Tick növelését végezzük.
     */
    public void forwardOneStep() {
        for (Steppable s : stepables) {
            s.step(canvas);
        }

        //Collision detection
        for (int i = 0; i < stepables.size(); ++i) {
            Steppable s1 = stepables.get(i);
            for (int j = i + 1; j < stepables.size(); ++j) {
                Steppable s2 = stepables.get(j);
                if (s2.isCollidedWith(s1)) {
                    s2.hitBy((Dot) s1);
                }
            }
        }

        if (!removeInTheEnd.isEmpty()) {
            for (Steppable s : removeInTheEnd) {
                stepables.remove(s);
            }
            removeInTheEnd.clear();
        }
        if (!addInTheEnd.isEmpty()) {
            stepables.addAll(addInTheEnd);
            addInTheEnd.clear();
        }

        for (Steppable s : stepables) {
            s.refresh(canvas);
        }
        currTickIncrease();
    }

    /**
     * Elküldti az adatokat a SimulationStatisticsStore-nak, majd lépteti a kört
     */
    private void currTickIncrease() {
        sendData();
        currTick++;
    }

    /**
     * Kilépéskor lezárja és üríti a szükséges dolgokat
     */
    public void exit() {
        timer.cancel();
        stepables.clear();
        removeInTheEnd.clear();
        addInTheEnd.clear();
        sss.clearAll();
    }

    /**
     * Gyorsítja a felhasználó által érzett idő telését
     */
    public void speedUp() {
        if (oneTickInMs > 1) {
            oneTickInMs /= 2;
        }
    }

    /**
     * Lassítja a felhasználó által érzett idő telését
     */
    public void speedDown() {
        if (oneTickInMs < 32) {
            oneTickInMs *= 2;
        }
    }

    /**
     * Minden sendDataPeriod-ban elküldi a SimulationStatisticsStore-nak az éppen aktuális adatokat
     */
    private void sendData() {
        if (currTick % sendDataPeriod == 0) {
            sss.addDeathsChange(currTick, deadCnt);
            sss.addHealChange(currTick, healedCnt);
            sss.addInfectionChange(currTick, infectedCnt);
            sss.addPopulationChange(currTick, neutralCnt);
        }
    }
}
