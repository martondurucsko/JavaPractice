package com.zh.hospital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient implements Runnable {
    private List<Vizsgálat> route = new ArrayList<>();
    Hospital hospital;
    private final String patientId;


    public String getPatientId() {
        return patientId;
    }

    public List<Vizsgálat> getRoute() {
        return route;
    }

    public Patient(String patientId, List<Vizsgálat> route) {
        this.patientId = patientId;
        this.route = route;
    }

    /// összekapcsolás
    public void setHospital(Hospital hospital){
        this.hospital=hospital;
    }

    public String getState (){
        return "PATIENT " + this.patientId + " route is (" + this.route.toString() +")";
    }


    @Override
    public void run() {
        try {
            if (route.isEmpty()) {
                hospital.addLog("nincs betöltve vizsgalatok");
            }

            for (int i = 0; i < getRoute().size(); i++) {
                Vizsgálat actVizsgalat = getRoute().get(i); // patient vizsgalata
                int actRoomId = actVizsgalat.getRoomId();   // vizsgalat roomId-ja
                Room actRoom = hospital.getRoomById(actRoomId);// roomId roomja - patient aktualis roomja

                //a routeban vizsgalatok vannak
                // a vizsgalatnak van roomid-ja
                // ossze kellene kotni hogy a roomba be tudjam tenni a patientet

                actRoom.enteringRoom(this);
                hospital.addLog(patientId + " Belépett " + actRoom + ", vizsgálat: " + actVizsgalat.getVizsgalatNev());
                Thread.sleep(actVizsgalat.getTestDurationInMs());

                actRoom.leavingRoom(this);
                hospital.addLog(patientId + "kilépett: " + actRoom);

                /*
                ez itt nem kell
                Vizsgálat nextVizsgalat = getRoute().get(i+1);
                int nextRoom = nextVizsgalat.getRoomId();
                */
            }

            //kicserélni currentet nextre


        }catch (RuntimeException | InterruptedException ex){
            hospital.addLog(patientId + "error");
        }

    }
}
