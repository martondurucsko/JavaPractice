package com.zh.cargohub;

public class SimulationSnapshot {

    private boolean loaded;
    private boolean running;
    private String scenarioName;
    private int botCount;
    private int threadCount;
    private int finishedBotCount;
    private int logCount;

    public SimulationSnapshot(boolean loaded,
                              boolean running,
                              String scenarioName,
                              int botCount,
                              int threadCount,
                              int finishedBotCount,
                              int logCount) {
        this.loaded = loaded;
        this.running = running;
        this.scenarioName = scenarioName;
        this.botCount = botCount;
        this.threadCount = threadCount;
        this.finishedBotCount = finishedBotCount;
        this.logCount = logCount;
    }

    @Override
    public String toString() {
        return "Loaded: " + loaded +
                "\nRunning: " + running +
                "\nScenario: " + scenarioName +
                "\nBots: " + botCount +
                "\nThreads: " + threadCount +
                "\nFinished bots: " + finishedBotCount +
                "\nLogs: " + logCount;
    }
}