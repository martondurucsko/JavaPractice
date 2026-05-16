package com.zh.restaurant;

import java.util.ArrayList;
import java.util.List;

//komunikal  guival
public class EtteremManager {

    Étterem etterem;
    ÉtteremLoader etteremLoader = new ÉtteremLoader();
    List <String> logs = new ArrayList<>();

    public void loadSimulation(String file){
        etterem = etteremLoader.load(file);
    }

    public void startsimulation(){
        if (etterem==null){
            return;
        }
        etterem.startEtterem();
    }

    public void stopSimulation(){
        if (etterem==null){
            return;
        }
        etterem.stopEtterem();
    }


    public String getStatus(){
        if (etterem == null) {
            return "Nincs betöltött szimuláció.";
        }
        return etterem.getState();
    }

    public void addLog(String message){

        logs.add(message+"\n");
    }


}
