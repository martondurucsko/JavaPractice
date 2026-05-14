package com.sysmon.airport;
// egyszerre egy repulo lehet
//entering csomopont
// - while occupied -> wait
// - occupied = true és notifyall
// leave CSomopont


public class CsomoPont {

    private final int csomopontId;
    private final Object lock = new Object();
    private final String csomopontType;
    private boolean occupied = false;

    public CsomoPont(int csomopontId, String csomopontType) {
        this.csomopontId = csomopontId;
        this.csomopontType = csomopontType;
    }

    public void enteringCsomopont (String planeId) throws InterruptedException {
        synchronized (lock){
            //kell egy ellenőrzés
            while(occupied){
                lock.wait();
            }

            int actCsomoPontId = csomopontId;
            occupied=true;
            System.out.println("Plane: " + planeId + "is entering Node" + actCsomoPontId);
        }
    }


    public void leavingCsomopont (String planeId){
        synchronized (lock) {
            int actCsomopontId = csomopontId;
            if (!occupied){
                System.out.println("Ures a csomopont" + actCsomopontId);
            }
            occupied = false;
            System.out.println("Plane: " + planeId + "is leaving node: " +actCsomopontId);
            lock.notifyAll();

        }
    }

    public String getStateText(){
        synchronized (lock) {
            if (occupied){
                return "csomopont " + csomopontId + " " + csomopontType + " is occupied";
            }
            return "csomopont" + csomopontId + " " + csomopontType + " is free";
        }
    }
    public int getCsomopontId(){
        return csomopontId;
    }




}
