package simulator;

import javafx.scene.canvas.Canvas;
import simulatorComponents.Dot;
import simulatorComponents.SimulationMap;
import simulatorComponents.dotTypes;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * SimulationPlayer osztaly.
 * Egy szimulacio lejatszasahoz szukseges adatokat es fuggvenyeket tarolja.
 */
public class SimulationPlayer extends TimerTask {
    /**
     * Leptetheto dolgok taroloja
     */
    static ArrayList<Steppable> stepables;
    /**
     * Olyan leptetheto dolgok taroloja, amit el kell tavolitani a steppables kozul a Tick vegen
     */
    static ArrayList<Steppable> removeInTheEnd;
    /**
     * Olyan leptetheto dolgok taroloja, amit hozza kell adni a leptetheto dolgok koze a Tick vegen
     */
    static ArrayList<Steppable> addInTheEnd;
    /**
     * eppen fertozo Dotok szama
     */
    static int infectedCnt;
    /**
     * eppen halott Dotok szama
     */
    static int deadCnt;
    /**
     * eppen egeszsegese Dotok szama
     */
    static int healedCnt;
    /**
     * eppen semleges Dotok szama
     */
    static int neutralCnt;
    /**
     * Egy Tick Ms-ban merve
     */
    static int oneTickInMs;
    /**
     * Szimulacio Statisztikat tarolo osztaly
     * Pufferkent funkcional
     */
    SimulationStatisticsStore sss;
    /**
     * Ket kor kozott eltelt minimalis periodusido
     */
    int minPeriod;
    /**
     * Idozito, a koronkent torteno lepesert felel
     */
    Timer timer;
    /**
     * Le van-e szuneteltetve a szimulacio
     */
    boolean paused;
    /**
     * A canvas, amire rajzolunk
     */
    Canvas canvas;
    /**
     * eppen aktualis kor sorszama
     */
    int currTick;
    /**
     * Szimulacio kezdete ota eltelt ido
     */
    int millisecondsElapsed;
    /**
     * Adatkuldes gyakorisaga
     */
    int sendDataPeriod = 10;

    /**
     * SimulationPlayer konstruktora
     *
     * @param sim    A kapott SimulationTemplate
     * @param canvas A kapott Canvas
     * @param sss    A kapott statisztika tarolo, ami pufferkent funkcional
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
     * Lappangasi idot visszado fuggveny
     *
     * @return Lappangasi ido Tickekben merve
     */
    public static int getIncubationPeriod() {
        return 500;
    }

    /**
     * Halott Dot eltunesi idejet hatarozza meg
     *
     * @return Eltunesi ido Tickekben merve
     */
    public static int getRemoveTime() {
        return 150;
    }

    /**
     * Hozzaadja a Steppable dolgot az eltavolitandok listajahoz
     *
     * @param st Az eltavolitando Steppable
     */
    static public void removeSteppable(Steppable st) {
        removeInTheEnd.add(st);
    }

    /**
     * Hozzaadja a Steppable dolgot a hozzaadandoak listajahoz
     *
     * @param st A hozzaadando Steppable
     */
    static public void addSteppable(Steppable st) {
        addInTheEnd.add(st);
    }

    /**
     * Viszzadja az eltavolitandoak listajat
     *
     * @return Az eltavolitandoak listaja
     */
    public static ArrayList<Steppable> getRemove() {
        return removeInTheEnd;
    }

    /**
     * uj fertozott eseten noveli a fertozotteket es csokkenti a semleges Dotok szamat
     */
    public static void addInfectedDot() {
        infectedCnt++;
        neutralCnt--;
    }

    /**
     * uj gyogyult eseten noveli a gyogyultakat es csokkenti a fertozo Dotok szamat
     */
    public static void addHealedDot() {
        healedCnt++;
        infectedCnt--;
    }

    /**
     * uj fertozott eseten noveli a halottak es csokkenti a fertozo Dotok szamat
     */
    public static void addDeadDot() {
        deadCnt++;
        infectedCnt--;
    }

    /**
     * A Canvasrol kilogo dotokat visszarakja a Canvas-ra
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
     * Frissiti a teljes szimulacio tartalmat
     * Az elso Steppable mindig a palya maga
     *
     * @param c A kapott Canvas
     */
    public void refresh(Canvas c) {
        for (Steppable s : stepables) {
            s.refresh(c);
        }
    }

    /**
     * Timer hivja minPeriod idokozonkent.
     * Ha nincs szuneteltetve, akkor oneTickInMs ms-kent megtesz egy lepest
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
     * negalja a paused valtozo erteket
     */
    public void changePlayAndPause() {
        paused = !paused;
    }

    /**
     * Egy lepest szimulal le.
     * Eloszor mindenki lep. Utana az utkozesek jonnek, majd eltavolitjuk es hozzaadjuk a szukseges Steppable-oket,
     * majd Frissitjuk a Canvast. Vegul a jelentlegi Tick noveleset vegezzuk.
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
     * Elkuldti az adatokat a SimulationStatisticsStore-nak, majd lepteti a kort
     */
    private void currTickIncrease() {
        sendData();
        currTick++;
    }

    /**
     * Kilepeskor lezarja es uriti a szukseges dolgokat
     */
    public void exit() {
        timer.cancel();
        stepables.clear();
        removeInTheEnd.clear();
        addInTheEnd.clear();
        sss.clearAll();
    }

    /**
     * Gyorsitja a felhasznalo altal erzett ido teleset
     */
    public void speedUp() {
        if (oneTickInMs > 1) {
            oneTickInMs /= 2;
        }
    }

    /**
     * Lassitja a felhasznalo altal erzett ido teleset
     */
    public void speedDown() {
        if (oneTickInMs < 32) {
            oneTickInMs *= 2;
        }
    }

    /**
     * Minden sendDataPeriod-ban elkuldi a SimulationStatisticsStore-nak az eppen aktualis adatokat
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
