package com.zh.kikoto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KikotoManager {
    private  Map<Integer, Kikoto> kikotoById;
    private List<ViziFolyoso> viziFolyosoList;
    private  List<Hajo> hajok;

    public KikotoManager() {
        this.hajok = new ArrayList<>();
        this.viziFolyosoList = new ArrayList<>();
        this.kikotoById = new HashMap<>();
    }

    public List<Hajo> getHajok() {
        return hajok;
    }

    public Map<Integer, Kikoto> getKikotoById() {
        return kikotoById;
    }

    public List<ViziFolyoso> getViziFolyosoList() {
        return viziFolyosoList;
    }
    List<Thread> hajoThreads = new ArrayList<>();
    List <String> logs = new ArrayList<>();



    /// az EGÉSZ KIKOTO
    KikotoManager(List<Hajo> hajok, List<ViziFolyoso> viziFolyosoList, Map<Integer, Kikoto> kikotoById){
        this.hajok = hajok;
        this.viziFolyosoList = viziFolyosoList;
        this.kikotoById = kikotoById;

        for (Hajo hajo:hajok){
            hajo.setKikotoManager(this);
        }
        for (ViziFolyoso folyoso:viziFolyosoList){
            folyoso.setKikotoManager(this);
        }

        for (Kikoto kikoto: kikotoById.values()){
            kikoto.setKikotoManager(this);
        }

    }




    KikotoManager loadSimulation(String fileName){

        KikotoLoader loader = new KikotoLoader();
        KikotoManager loadedManager = loader.load(fileName);


        return loadedManager;

    }

    public void startSimulation(){
        if (hajok==null || hajok.isEmpty()){
            System.out.println("nincs betoltve sim");
            return;
        }

        if (!hajoThreads.isEmpty()){
            System.out.println("sim already started");
            return;
        }

        for (Hajo hajo:hajok){
            hajo.setKikotoManager(this);
            Thread hajoThread = new Thread(hajo);
            hajoThreads.add(hajoThread);
            hajoThread.start();
        }


    }

    public void stopSimulation(){
        if (this==null){
            System.out.println("nincs futo hajo");
        }
        for (Thread t:hajoThreads){
            t.interrupt();
        }

    }

    public String addLog(String message){
        logs.add(message);
        return logs.toString();
    }



}
