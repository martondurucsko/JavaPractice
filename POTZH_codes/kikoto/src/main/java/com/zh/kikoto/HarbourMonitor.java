package com.zh.kikoto;

//logika
// ezzel beszél a GUI


import java.util.ArrayList;
import java.util.List;

public class HarbourMonitor {
    private final Object lock = new Object();
    private final List<String> logs = new ArrayList<>();
    private int maxCapacity = 3; //ennyien fernek el a kikotoben
    private int capacity;
    List <String> bennlevoHajok = new ArrayList<>();



    HarbourMonitor(int maxCapacity){
        this.maxCapacity=maxCapacity;
    }


    public void enteringHarbour(String boatId) throws InterruptedException {
        synchronized (lock){
            while (isFull()){
                //addlog, be akar menni de tele
                lock.wait();
            }
            addLog(boatId + " is entering harbour");
            bennlevoHajok.add(boatId);
            capacity++;
            //resource megkapja
            //logolas
        }
    }

    public void leaveHarbour(String boatId){
        synchronized (lock){
            String actHajo = boatId;
            if (bennlevoHajok.remove(actHajo)) {
                capacity--;
                addLog(actHajo + " is leaving harbour");
                //elobb hasznalom az adatot (log)
                //utana nullazok
                lock.notifyAll();
            }
        }
    }

    public boolean isFull(){
        synchronized (lock) {
            boolean isFull = false;
            if (capacity == maxCapacity) {
                return isFull = true;
            }
            return isFull = false;
        }
    }

    private boolean isEmpty() {
        synchronized (lock) {
            if (capacity == 0) {
                return true;
            }
            return false;
        }
    }

    public String getStatus() {
        // ha ures akkor dummyval
        // ha nem akkor mehet fel rendesen
        synchronized (lock) {
            StringBuilder sb = new StringBuilder();
            sb.append("A BENN LEVO HAJOK: \n");
            if (isEmpty()) {
                sb.append("a kikoto ures");
            } else {
                for (int i = 0; i < bennlevoHajok.size(); i++) {
                    sb.append(bennlevoHajok.get(i)).append("\n");
                }
            }
            return sb.toString();

        }
    }

    public void addLog(String message) {
        synchronized (lock) {
            logs.add(message);
        }
    }
    public String getLogsToString() {
        synchronized (lock) {
            StringBuilder sb = new StringBuilder();
            for (String log:logs){
                sb.append(log + "\n");
            }
            return sb.toString();
        }
    }


}
