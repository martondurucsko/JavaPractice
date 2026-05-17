package com.zh.kikoto;

import java.util.List;

public class Hajo implements Runnable{


    private final List<ViziFolyoso> route;

    public String getHajoId() {
        return hajoId;
    }

    public List<ViziFolyoso> getRoute() {
        return route;
    }

    private final String hajoId;

    private KikotoManager manager;

    public Hajo(String hajoId, List<ViziFolyoso> route) {
        this.hajoId = hajoId;
        this.route = route;

    }

    public void setKikotoManager (KikotoManager manager){
        this.manager = manager;
    }

    @Override
    public void run() {
        // egy hajo elete
        try {
        Hajo actHajo = this;

        //elson vegigmegy az elson bedockol es utana megy bele a listaba

        for (ViziFolyoso viziFolyoso: actHajo.route){
            ViziFolyoso actFolyoso=viziFolyoso; //van neki startja ,stopja, neve, ideje

            Thread.sleep(actFolyoso.getTravelTimeInMs());

            Kikoto kikoto = manager.getKikotoById().get(actFolyoso.getEndPoint());

            kikoto.docking(this);
            System.out.println(actHajo.hajoId + "BEDOCKOLT: " + actFolyoso.getEndPoint());

            Thread.sleep(2000); //ennyit van benn

            kikoto.leavingDock(this);
            System.out.println(actHajo.hajoId + "elhagjya: " +actFolyoso.getEndPoint());
        }
        System.out.println(actHajo.hajoId + "végzett az utjával");

        } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }




    }
