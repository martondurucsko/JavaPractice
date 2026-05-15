package com.zh.hospital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Hospital {
    List <Patient> patients = new ArrayList<>();
    List <Room> rooms = new ArrayList<>();
    Map <Integer , Room> roomsById = new HashMap<>();
    Map <String,Vizsgálat> vizsgalatByName = new HashMap<>();

    List<Thread> patientThreads = new ArrayList<>();
    private final List <String> logs = new ArrayList<>();

    public Hospital(List<Patient> patients, List<Room> rooms,Map<Integer, Room> roomsById,Map <String,Vizsgálat> vizsgalatByName) {
        this.roomsById = roomsById;
        this.rooms = rooms;
        this.patients = patients;
        this.vizsgalatByName = vizsgalatByName;

        /// A LEGFONTOSABB - összekötés
        for (Patient patient:patients){
            patient.setHospital(this);
        }
    }
    public Room getRoomById(int id){
        return roomsById.get(id);
    }


    //startHospital - patienteket csinal Thread elinditja


    public void startHospital(){

        //bejarja a listat és minden eleméből patient threadet csinal
        for (Patient patient:patients) {
            Thread patientThread = new Thread(patient);
            patientThreads.add(patientThread);

            patientThread.start();
        }


    }

    //loadHospital - hospitalloader loader(String file)
/*
    public void load(String file){
        HospitalLoader loader = new HospitalLoader();
        loader.load(file);
    }
*/

    //stopHospital - void (interruptolja a szalakat)

    public void stopHospital(){
        for (int i = 0; i <patientThreads.size() ; i++) {
            Thread actThread = patientThreads.get(i);
            actThread.interrupt();
        }
    }




    //van getState:
    // vizsgalat
    //patientnek
    // roomnak
    public synchronized String getState(){
        StringBuilder sb = new StringBuilder();


        sb.append("VIZSGALATOK: \n");
        for (Vizsgálat vizsgálat: vizsgalatByName.values()) {
            sb.append(vizsgálat.getState()).append("\n");
        }

        sb.append("\nPatients:\n");
        for (Patient patient:patients){
            sb.append(patient.getState()).append("\n");
        }

        sb.append("\nROOMS:\n");
        for (Room room : rooms){
            sb.append(room.getStateText()).append("\n");
        }

        return sb.toString();

    }

    public synchronized void addLog(String message){
        logs.add(message);
    }
    public synchronized String getLogString(){
        return String.join("\n",logs);
    }


}
