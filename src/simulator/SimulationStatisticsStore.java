package simulator;

import javafx.util.Pair;

import java.util.concurrent.ConcurrentLinkedQueue;

//TODO log to file

/**
 * Puffer tarolo a stasztikai adatok tarolasara
 */
public class SimulationStatisticsStore {
    /**
     * population adatok puffere.
     * Azert erre esett a valasztasom, mert igy a Concurrent Modification Exception-t el tudom kerulni,
     * mert tortenthet olyan, hogy eppen olvasom a Puffert, mikor a vegere mar kerul az uj adat
     */
    ConcurrentLinkedQueue<Pair<Number, Number>> populationQueue = new ConcurrentLinkedQueue<>();
    /**
     * halalozasi adatok puffere
     */
    ConcurrentLinkedQueue<Pair<Number, Number>> deathsQueue = new ConcurrentLinkedQueue<>();
    /**
     * fertozesi adatok puffere
     */
    ConcurrentLinkedQueue<Pair<Number, Number>> infectionsQueue = new ConcurrentLinkedQueue<>();
    /**
     * gyogyulasi adatok puffere
     */
    ConcurrentLinkedQueue<Pair<Number, Number>> healsQueue = new ConcurrentLinkedQueue<>();

    /**
     * SimulationStatisticsStore konstruktor
     */
    public SimulationStatisticsStore() {

    }

    /**
     * Hozzaad egy uj erteket a populationQueue-hoz
     *
     * @param time Idobelyeg (Tick-ben)
     * @param val  Az aktualis ertek
     */
    public void addPopulationChange(Number time, Number val) {
        populationQueue.add(new Pair<>(time, val));
    }

    /**
     * Hozzaad egy uj erteket a deathsQueue-hoz
     *
     * @param time Idobelyeg (Tick-ben)
     * @param val  Az aktualis ertek
     */
    public void addDeathsChange(Number time, Number val) {
        deathsQueue.add(new Pair<>(time, val));
    }

    /**
     * Hozzaad egy uj erteket a infectionsQueue-hoz
     *
     * @param time Idobelyeg (Tick-ben)
     * @param val  Az aktualis ertek
     */
    public void addInfectionChange(Number time, Number val) {
        infectionsQueue.add(new Pair<>(time, val));
    }

    /**
     * Hozzaad egy uj erteket a healsQueue-hoz
     *
     * @param time Idobelyeg (Tick-ben)
     * @param val  Az aktualis ertek
     */
    public void addHealChange(Number time, Number val) {
        healsQueue.add(new Pair<>(time, val));
    }

    /**
     * Visszadja az infectionsQueue-t
     *
     * @return infectionsQueue
     */
    public ConcurrentLinkedQueue<Pair<Number, Number>> getInfectionsQueue() {
        return infectionsQueue;
    }

    /**
     * Visszadja a populationQueue-t
     *
     * @return populationQueue
     */
    public ConcurrentLinkedQueue<Pair<Number, Number>> getPopulationQueue() {
        return populationQueue;
    }

    /**
     * Visszadja a deathsQueue-t
     *
     * @return deathsQueue
     */
    public ConcurrentLinkedQueue<Pair<Number, Number>> getDeathsQueue() {
        return deathsQueue;
    }

    /**
     * Visszadja a healsQueue-t
     *
     * @return healsQueue
     */
    public ConcurrentLinkedQueue<Pair<Number, Number>> getHealsQueue() {
        return healsQueue;
    }

    /**
     * uriti az infectionsQueue-t
     */
    public void clearInfectionsQueue() {
        infectionsQueue.clear();
    }

    /**
     * uriti a populationQueue-t
     */
    public void clearPopulationQueue() {
        populationQueue.clear();
    }

    /**
     * uriti a deathsQueue-t
     */
    public void clearDeathsQueue() {
        deathsQueue.clear();
    }

    /**
     * uriti a healsQueue-t
     */
    public void clearHealsQueue() {
        healsQueue.clear();
    }

    /**
     * uriti az osszes Queue-t
     */
    public void clearAll() {
        infectionsQueue.clear();
        populationQueue.clear();
        deathsQueue.clear();
        healsQueue.clear();
    }
}
