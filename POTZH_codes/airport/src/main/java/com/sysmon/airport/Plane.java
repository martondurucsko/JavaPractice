package com.sysmon.airport;

// egy db plane nek a az elete

import java.util.List;

public class Plane implements Runnable{
    private Airport airport;
    private volatile boolean running = true;
    private final List<GuruloUt> route;
    private final String repuloId;


    public Plane(String repuloId, List<GuruloUt> route) {
        this.repuloId = repuloId;
        this.route = route;

    }


    public List<GuruloUt> getRoute() {
        return route;
    }

    public String getRepuloId() {
        return repuloId;
    }


    //összekötés
    public void setAirport(Airport airport){
        this.airport = airport;
    }


    @Override
    public void run() {

        try {


            //amikor meghivja egy airport

            // meg kelll nézni hogy van e route
            if (route.isEmpty()) {
                airport.log(repuloId + "route is empty");
                return;
            }
            int currenId = route.get(0).getFrom();
            CsomoPont current = airport.getCsomopontById(currenId);

            current.enteringCsomopont(repuloId);
            airport.log(repuloId + "entered first csomopont");

            for (GuruloUt guruloUt:route){
                if (!running){
                    break;
                }

                airport.log(repuloId + " elindult ezen az uton: " + guruloUt.getGuruloUtNev());

                Thread.sleep(guruloUt.getTravelTimeInMs()*1000);

                int nextId = guruloUt.getTo();
                CsomoPont next = airport.getCsomopontById(nextId);

                next.enteringCsomopont(repuloId);
                airport.log(repuloId + " belépett ebbe a csomopontba " + nextId);

                current.leavingCsomopont(repuloId);
                airport.log(repuloId + " kilepett a " + currenId);

                Thread.sleep(300);

                current=next;
                currenId = nextId;
            }
            current.leavingCsomopont(repuloId);
            airport.log(repuloId + " is finished and leaving csomopont" + currenId);


        }catch (InterruptedException e){
            airport.log(repuloId + " interrupted");
            Thread.currentThread().interrupt();
        }




    }
    public void stop(){
        running = false;
    }
}
