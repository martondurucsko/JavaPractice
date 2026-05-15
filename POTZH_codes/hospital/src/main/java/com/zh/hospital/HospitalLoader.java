package com.zh.hospital;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HospitalLoader {
    List <Patient>patients = new ArrayList<>();
    Map<Integer,Room> roomsById= new HashMap<>();
    Map <String,Vizsgálat> vizsgalatByName = new HashMap<>();
    List<Room> rooms = new ArrayList<>();


    //roomsById ()
    //VizsgálatByName
    //
    public Hospital load(String file){
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line =  reader.readLine();
            String[] firtsParts=line.trim().split("\\s+");

            if (firtsParts.length!=3){
                throw new IOException("a file elso soraban rosszul van feltuntetve az adat");
            }

            int allRoomCount = Integer.parseInt(firtsParts[0]);
            int allVizsgalatCount = Integer.parseInt(firtsParts[1]);
            int allPatientCount = Integer.parseInt(firtsParts[2]);


            //[room][vizsgalat][patient]


            //rooom
            for (int i = 0; i < allRoomCount; i++) {
                String roomLine = reader.readLine();
                String[]roomsParts = roomLine.trim().split("\\s+");

                if (roomsParts.length!=2){
                    throw new IOException("ERROR - roomparts must be 2 values : [id][name]");
                }
                int roomId = Integer.parseInt(roomsParts[0]);
                String roomName = roomsParts[1];
                Room actRoom = new Room(roomId,roomName);
                rooms.add(actRoom);
                roomsById.put(roomId,actRoom);
            }

            //vizsgalat
            for (int i = 0; i < allVizsgalatCount; i++) {
                String vizsgalatLine = reader.readLine();
                String[] vizsgalatParts = vizsgalatLine.trim().split("\\s+");

                if (vizsgalatParts.length!=3){
                    throw new IOException("ERROR - vizsgalparts must be 3 values");
                }
                String vizsgalatName=vizsgalatParts[0];
                int vizsgalatInRoom = Integer.parseInt(vizsgalatParts[1]);
                int vizsgalatDurationInMs = Integer.parseInt(vizsgalatParts[2]);

                Vizsgálat actVizsgalat = new Vizsgálat(vizsgalatName,vizsgalatDurationInMs,vizsgalatInRoom);
                vizsgalatByName.put(vizsgalatName,actVizsgalat);
            }


            //patient
            for (int i = 0; i < allPatientCount; i++) {
                String patientLine = reader.readLine();
                String[] patientParts = patientLine.trim().split("\\s+");

                String patientId = patientParts[0];
                int patientRouteLenght = Integer.parseInt(patientParts[1]);

                List<Vizsgálat> route = new ArrayList<>(); ///belso forban hasznaljuk csak
                for (int j = 0; j < patientRouteLenght; j++) {
                    String actVizsgalatName = patientParts[j+2]; //ez egy string
                    Vizsgálat actVizsgalat = vizsgalatByName.get(actVizsgalatName); // ez egy vizsgalat

                    route.add(actVizsgalat);
                }
                /// külső forban
                Patient patient = new Patient(patientId,route);
                patients.add(patient);
            }

            return new Hospital(patients, rooms, roomsById,vizsgalatByName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
