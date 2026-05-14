package com.sysmon.airport;

// adatstrukturak, elinditani egy plane-t


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Airport {

    private final List<GuruloUt> route = new ArrayList<>();
    private Map<Integer, CsomoPont> csomoPontById = new HashMap<>(); //by id nem NAME
    private final List<Thread> planeThreads = new ArrayList<>();
    private Map<String, GuruloUt> guruloUtByName = new HashMap<>();
    // nem tudom miert az ai mondta...
    private final List<String> logs = new ArrayList<>();
    private final List<Plane> planes;

    public Airport(Map<Integer, CsomoPont> csomoPontById, Map<String, GuruloUt> guruloUtByName, List<Plane> planes) {
        this.csomoPontById = csomoPontById;
        this.guruloUtByName = guruloUtByName;
        this.planes = planes;

        //összekötés!!
        //minden plane megkapja az airport referenciat
        for (Plane plane : planes){
            plane.setAirport(this);
        }


    }

    public CsomoPont getCsomopontById(int id){
        return csomoPontById.get(id);
    }

    public void startSimulation() {
        for (Plane plane : planes) {
            Thread thread = new Thread(plane);
            planeThreads.add(thread);
            thread.start();
        }
    }


    public void stopSimulation(){
        for (Plane plane:planes){
            plane.stop();
        }
        for (Thread thread:planeThreads){
            thread.interrupt();
        }
    }

    public synchronized void log(String message){
        logs.add(message);
        System.out.println(message);
    }

    public synchronized String getState(){
        StringBuilder sb = new StringBuilder();

        sb.append("Csomopontok\n");

        for (CsomoPont csomoPont:csomoPontById.values()){
            sb.append(csomoPont.getStateText()).append("\n");
        }
        sb.append("\nPlanes:\n");

        for (Plane plane:planes){
            sb.append(plane.getRepuloId()).append("\n");
        }

        sb.append("\nLogs:\n");
        for (String log:logs){
            sb.append(log).append("\n");
        }

        return sb.toString();

    }


}
