package com.zh.cargohub;

public class Hub {

    private String id;
    private int capacity;
    private int currentBots;
    private long crossingTimeMillis;

    public Hub(String id, int capacity, long crossingTimeMillis) {
        this.id = id;
        this.capacity = capacity;
        this.crossingTimeMillis = crossingTimeMillis;
        this.currentBots = 0;
    }

    public String getId() {
        return id;
    }

    public synchronized void enter(String botId, SimulationManager manager) throws InterruptedException {
        while (currentBots >= capacity && manager.isRunning()) {
            wait();
        }

        if (!manager.isRunning()) {
            throw new InterruptedException();
        }

        currentBots++;
        manager.addLog(botId + " entered hub " + id);

        Thread.sleep(crossingTimeMillis);
    }

    public synchronized void leave(String botId, SimulationManager manager) {
        currentBots--;
        manager.addLog(botId + " left hub " + id);
        notifyAll();
    }

    public synchronized int getCurrentBots() {
        return currentBots;
    }

    public int getCapacity() {
        return capacity;
    }

    public long getCrossingTimeMillis() {
        return crossingTimeMillis;
    }
}