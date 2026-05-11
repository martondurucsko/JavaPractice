package com.sysmon.hajogui;

import java.util.HashMap;
import java.util.Map;

public class HarborMonitor {

    private final int dockCount;

    private final Map<Integer,String> dockToBoat= new HashMap<>();
    private final Map <String, Integer> boatToDock = new HashMap<>();
    private final StringBuilder log = new StringBuilder();
    private final Object lock = new Object();

    public HarborMonitor (int dockCount){
        this.dockCount=dockCount;
        for (int i = 0; i < dockCount; i++) {
            dockToBoat.put(i,null);
        }
    }



    //indexes isfree -1 ha nincs i ha megtalalja
    private int isFreeIndex(){
        for (int i : dockToBoat.keySet()) {
            if (dockToBoat.get(i)==null){  // ha ures hely visszaadja
                return i;
            }
        }
        return -1;
    }

    //loglas
    private void addLog(String message){
        log.append(message).append("\n");
    }

    //snapshot - bekapja a dockToBoat-ot
    public Map<Integer,String> snapshot() {
        synchronized (lock) {
            return new HashMap<>(dockToBoat);
        }
    }

    //logsnapshot
    // guinak kell hogy string legyen
    public String logSnapshot(){
        synchronized (lock){
            return log.toString();
        }
    }



    public void enterHarbor(String hajoId) throws InterruptedException {
        synchronized (lock){
            while (isFreeIndex()==-1){
                addLog(hajoId + "ez a hajo most várakozik, nincs szabad kikoto");
                lock.wait();
            }
            int dockIndex = isFreeIndex();

            dockToBoat.put(dockIndex,hajoId);
            boatToDock.put(hajoId,dockIndex);

            addLog(hajoId + "sikeresen beállt a " + dockIndex + "kikotohelyre");
            lock.notifyAll();

        }
    }

    public void leaveHarbor(String boatId){
        synchronized (lock){
            Integer parkingAt = boatToDock.get(boatId);

            if (parkingAt==null){
                addLog("nem tudja elhagyni mert nincs benn");
                return;
            }

            boatToDock.remove(boatId);
            dockToBoat.remove(parkingAt,null);
            addLog(boatId + "hajo elhagyja a" + parkingAt + "kikotot");
            lock.notifyAll();
        }
    }

}
