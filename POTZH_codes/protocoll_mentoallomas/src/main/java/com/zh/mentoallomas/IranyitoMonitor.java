package com.zh.mentoallomas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IranyitoMonitor {
    Map<String,String> mentoIdByAvaiblity = new HashMap<>(); // BUSY/AVAILABLE - mentoid

    List<String> locations = new ArrayList<>();
    Map<String, String> locationByMento = new HashMap<>();      //mento  -- location



    //midnen Stringeket adjon ki es akkor be tudom rakni oket a responseba
    // ha uj jon akkor berakom az avaiblebe


    public synchronized String available(String ambulanceId) throws InterruptedException {
        while (mentoIdByAvaiblity.get(ambulanceId).equals("BUSY")){
            wait();
            return ambulanceId + "BUSY";
        }

        mentoIdByAvaiblity.put(ambulanceId, "AVAILABLE");
        locationByMento.remove(ambulanceId);

        notifyAll();
        return ambulanceId + " OK AVAILABLE";

    }

    public synchronized String alerted(String location){
        String actMento = mentoIdByAvaiblity.get("AVAILABLE");

        locations.add(location);
        locationByMento.put(actMento,location);

        mentoIdByAvaiblity.put(actMento,"BUSY");

        notifyAll();

        return "OK ASSIGNED "+ actMento + " " +location;

        //a mentot falsera allitom
        // berakom a locationjet
        // berakom a location-t a location listaba
    }


    public synchronized String done(String ambulanceID){
        //listabol kiveszem a loctiont
        // es a location by mentot removeolom a mentot
        String actLocation=locationByMento.get(ambulanceID);
        locations.remove(actLocation);
        locationByMento.remove(ambulanceID);


        mentoIdByAvaiblity.replace(ambulanceID,"AVAILABLE");


        notifyAll();
        //locationbool kiveszem
        return "DONE";
        //a mentot ujra avaible re allitoom
    }




    public synchronized String getState(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n MENTO ALLAPOTOK \n");

        for (String ambulanceId : mentoIdByAvaiblity.keySet()) {
            String state = mentoIdByAvaiblity.get(ambulanceId);

            sb.append(ambulanceId).append(" ").append(state);
            if (state.equals("BUSY")){
                sb.append(" ").append(locationByMento.get(ambulanceId));
            }
            sb.append("\n");
        }


        return sb.toString();
        // visszadja forral a mapben levo adatokat "\n"-el
    }


    public boolean isRegistered(String ambulanceId){
        for(String s: mentoIdByAvaiblity.keySet())
        {
            if (s.contains(ambulanceId)){
                return true;
            }
        }
        return false;
    }
    public void addToRegistereds(String ambulanceId){
        mentoIdByAvaiblity.put(ambulanceId,"AVAILABLE");
    }


    public String addLog(String message){
        return message;
    }


}
