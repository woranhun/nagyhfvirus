package simulator;

import javafx.util.Pair;

import java.util.concurrent.ConcurrentLinkedQueue;

//TODO log to file

/**
 * Puffer tároló a stasztikai adatok tárolására
 */
public class SimulationStatisticsStore {
    /**
     * population adatok puffere.
     * Azért erre esett a választásom, mert így a Concurrent Modification Exception-t el tudom kerülni,
     * mert történthet olyan, hogy éppen olvasom a Puffert, mikör a végére már kerül az új adat
     */
    ConcurrentLinkedQueue<Pair<Number, Number>> populationQueue = new ConcurrentLinkedQueue<>();
    /**
     * halálozási adatok puffere
     */
    ConcurrentLinkedQueue<Pair<Number, Number>> deathsQueue = new ConcurrentLinkedQueue<>();
    /**
     * fertőzési adatok puffere
     */
    ConcurrentLinkedQueue<Pair<Number, Number>> infectionsQueue = new ConcurrentLinkedQueue<>();
    /**
     * gyógyulási adatok puffere
     */
    ConcurrentLinkedQueue<Pair<Number, Number>> healsQueue = new ConcurrentLinkedQueue<>();

    /**
     * SimulationStatisticsStore konstruktor
     */
    public SimulationStatisticsStore() {

    }

    /**
     * Hozzáad egy új értéket a populationQueue-hoz
     *
     * @param time Időbélyeg (Tick-ben)
     * @param val  Az aktuális érték
     */
    public void addPopulationChange(Number time, Number val) {
        populationQueue.add(new Pair<>(time, val));
    }

    /**
     * Hozzáad egy új értéket a deathsQueue-hoz
     *
     * @param time Időbélyeg (Tick-ben)
     * @param val  Az aktuális érték
     */
    public void addDeathsChange(Number time, Number val) {
        deathsQueue.add(new Pair<>(time, val));
    }

    /**
     * Hozzáad egy új értéket a infectionsQueue-hoz
     *
     * @param time Időbélyeg (Tick-ben)
     * @param val  Az aktuális érték
     */
    public void addInfectionChange(Number time, Number val) {
        infectionsQueue.add(new Pair<>(time, val));
    }

    /**
     * Hozzáad egy új értéket a healsQueue-hoz
     *
     * @param time Időbélyeg (Tick-ben)
     * @param val  Az aktuális érték
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
     * Üríti az infectionsQueue-t
     */
    public void clearInfectionsQueue() {
        infectionsQueue.clear();
    }

    /**
     * Üríti a populationQueue-t
     */
    public void clearPopulationQueue() {
        populationQueue.clear();
    }

    /**
     * Üríti a deathsQueue-t
     */
    public void clearDeathsQueue() {
        deathsQueue.clear();
    }

    /**
     * Üríti a healsQueue-t
     */
    public void clearHealsQueue() {
        healsQueue.clear();
    }

    /**
     * Üríti az összes Queue-t
     */
    public void clearAll() {
        infectionsQueue.clear();
        populationQueue.clear();
        deathsQueue.clear();
        healsQueue.clear();
    }
}
