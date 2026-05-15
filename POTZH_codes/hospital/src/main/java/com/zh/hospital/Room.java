package com.zh.hospital;
//közös erőforrás ebben vannak a sync blokkok

//egy vizsgálat mindig egy adott helyseghez tartozik
// egy helységben egyszerre egy beteg lehet

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//
public class Room {

    private final int roomId;
    private final String roomName;

    private final Object lock = new Object();
    private final List <String> logs = new ArrayList<>();
    private Patient currnetPatient;

    private boolean occupied = false;

    public Room(int roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }


    public String getRoomName() {
        return roomName;
    }

    public int getRoomId() {
        return roomId;
    }


    //lehetne extran logolni hogy foglalt várjál egy kicsit
    /// extrra: ki lehetne egesziteni hogy a vizsgalatot is kiirja

    public void enteringRoom(Patient patientId) throws InterruptedException {
        synchronized (lock){
            while (occupied){ // ez is siman occupied
                lock.wait();
            }
            occupied = true;
            currnetPatient = patientId; // csak akkor ha tenyleg bejutott

            System.out.println(currnetPatient.getPatientId() + "has entered room: " + roomId);
        }
    }

    //lehetne logolni extran egy iffel hogy am nincsenek is benn
    public void leavingRoom(Patient patientId){
        synchronized (lock){
            System.out.println(currnetPatient.getPatientId() + "has left room: " +roomId);

            occupied = false;
            currnetPatient = null; // ha tenyleg kiment
            lock.notifyAll();

        }
    }


    public synchronized boolean isOccupied(){
        return occupied;
    }

    public synchronized String addLog(String message){
        String text = "";
        logs.add(text);
        return text += message + "\n";
    }

    /// lehet kell egy getlog amit stringé alakitok ez az hospitalban van


    public String getStateText(){
        synchronized (lock) {
            if (occupied){ // ez siman occupied
             return "Rooom: " + Room.this.getRoomId() + "is occupied";
            }
            return "Room: " + Room.this.getRoomId() + "is free";
        }
    }


}
