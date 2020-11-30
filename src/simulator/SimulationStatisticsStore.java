package simulator;

import javafx.util.Pair;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SimulationStatisticsStore {
    ConcurrentLinkedQueue<Pair<Number,Number>> populationQueue = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<Pair<Number,Number>> deathsQueue = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<Pair<Number,Number>> infectionsQueue = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<Pair<Number,Number>> healsQueue = new ConcurrentLinkedQueue<>();
    public SimulationStatisticsStore(){

    }
    public void addPopulationChange(Number time, Number val){
        populationQueue.add(new Pair<>(time,val));
    }
    public void addDeathsChange(Number time, Number val){
        deathsQueue.add(new Pair<>(time,val));
    }
    public void addInfectionChange(Number time, Number val){
        infectionsQueue.add(new Pair<>(time,val));
    }
    public void addHealChange(Number time, Number val){
        healsQueue.add(new Pair<>(time,val));
    }

    public ConcurrentLinkedQueue<Pair<Number,Number>> getInfectionsQueue() {
        return infectionsQueue;
    }
    public ConcurrentLinkedQueue<Pair<Number,Number>> getPopulationQueue() {
        return populationQueue;
    }
    public ConcurrentLinkedQueue<Pair<Number,Number>> getDeathsQueue() {
        return deathsQueue;
    }
    public ConcurrentLinkedQueue<Pair<Number,Number>> getHealsQueue() {
        return healsQueue;
    }
    public void clearInfectionsQueue(){
        infectionsQueue.clear();
    }

    public void clearPopulationQueue() {
        populationQueue.clear();
    }

    public void clearDeathsQueue() {
        deathsQueue.clear();
    }

    public void clearHealsQueue() {
        healsQueue.clear();
    }

    public void clearAll() {
        infectionsQueue.clear();
        populationQueue.clear();
        deathsQueue.clear();
        healsQueue.clear();
    }
}
