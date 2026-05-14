package com.sysmon.airport;

// az airportal összekoti  a gui-t

public class AirportSimulationManager {
    private Airport airport;

    public void load(String file){
        AirPortLoader loader = new AirPortLoader();
        airport = loader.load(file);
    }

    public void startSimulation(){
        if (airport==null){
            throw new IllegalStateException("no airport loaded");
        }
        airport.startSimulation();
    }

    public void stopsimulation(){
        if (airport != null){
            airport.startSimulation();
        }
    }

    public String getStateText(){
        if (airport==null){
            return "no airport loaded";
        }
        return airport.getState();
    }

}
