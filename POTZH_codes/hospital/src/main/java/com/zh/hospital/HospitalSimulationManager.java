package com.zh.hospital;

public class HospitalSimulationManager {
    Hospital hospital;

    //startSimulation

    public void startSimulation(){
        if (hospital==null){
            throw new IllegalStateException("no hospital loaded");
        }
        hospital.startHospital();
    }


    public void stopSimulation() {
        if (hospital != null) {
            hospital.stopHospital();
        }
    }

    public void loadHospital(String file){
        HospitalLoader loader = new HospitalLoader(); ///bar ezt lehet GUIBA kell
        hospital = loader.load(file);
    }

    //snapshot  //statearea
    public String getHospitalState(){
        if (hospital==null){
            throw new IllegalStateException("no hospital loaded");
        }
        return hospital.getState();
    }

    public String getHospitalLogs(){  // logarea
        return hospital.getLogString();
    }


}
