package com.zh.cargohub;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/// todo


public class SimulationManager {
    private Scenario scenario;

    private ArrayList<Thread> threads = new ArrayList<>();
    private ArrayList<String> logs = new ArrayList<>();

    //a stophoz es starthoz
    private boolean running = false;

    private int finishedBotCount = 0;


    public void startSimulation(){
    //szerintem van egy osztaly ahol le van irva a botrunnerek n db inicializalasa
    // corridor talan, ahol a semafor van
        // na azt kelll meghivni

        if (scenario==null){
            addLog("no scenario loaded");
            return;
        }

         if (running){
             addLog("Simulation already running");
         }

         running = true;
         finishedBotCount = 0;
         threads.clear();

         for (BotSpec bot : scenario.getBots()) {
             BotRunner runner = new BotRunner(bot,this);
             Thread t = new Thread(runner);
             threads.add(t);
             t.start();
         }
    }

    public void stopSimulation() {
        // ugyna ott kene legyen egy stop is ahol interrupt van
        // és interruptolni a threadet

        running = false;
        for (Thread t: threads){
            t.interrupt();
        }

        addLog("simulation stopped");
    }

    public void loadScenario(String path) throws IOException {
        //scenario loader kell hozza egy scenario is peldany
        scenario = ScenarioLoader.load(path);
        logs.clear();
        threads.clear();
        finishedBotCount = 0;
        running = false;

        System.out.println("Scenario loaded: " +scenario.getName());


    }

    public synchronized SimulationSnapshot getSnaphot(){
        //van to stringje ilyenkor vna valami turpissag
        // ez a refresh

        //boolean loaded = scenario != null;
        //String scenarioName = loaded ? scenario.getName() : "none";
        //int botCount = loaded ? scenario.getBots().size() : 0;

        //ha scenario null akkor dummy amugy meg getterek

        if (scenario==null){
            return new SimulationSnapshot(false,false, null,0, threads.size(), finishedBotCount,logs.size());
        }
        else {
            return new SimulationSnapshot(true,true, scenario.getName(), scenario.getBots().size(),threads.size(),finishedBotCount,logs.size());
        }

    }

    public synchronized String getLogsasText(){
        return logs.toString();

    }
    public synchronized boolean isRunning(){

        return running;
    }

    public synchronized void botFinished() {
        finishedBotCount++;
        if (scenario.getBots().size()<=finishedBotCount && running){
            running=false;
            addLog("Last Bot and simulation finished" );
        }
    }


    public synchronized void addLog(String message){
        String text = "";
        text += message + "\n";
        logs.add(text);
    }


}
