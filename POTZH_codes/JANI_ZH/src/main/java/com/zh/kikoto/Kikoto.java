package com.zh.kikoto;


import java.util.List;

//logika
//összekötés
//shared resource



public class Kikoto {
    private final int dockId;
    private boolean occupied = false;
    private KikotoManager kikotoManager;

    public Kikoto(int dockId) {
        this.dockId = dockId;
    }

    public void setKikotoManager(KikotoManager kikotoManager) {
        this.kikotoManager = kikotoManager;
    }



    public synchronized void docking(Hajo hajoId) throws InterruptedException {
        while (occupied){
            wait();
        }
        occupied=true;

    }

    public synchronized void leavingDock(Hajo leavingHajo){
        occupied=false;
        notifyAll();
    }




}
