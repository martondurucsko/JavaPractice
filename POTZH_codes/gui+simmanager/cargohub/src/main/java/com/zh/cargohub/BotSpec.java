package com.zh.cargohub;

import java.util.ArrayList;

public class BotSpec {

    private String id;
    private Hub startHub;
    private ArrayList<Corridor> route;

    public BotSpec(String id, Hub startHub, ArrayList<Corridor> route) {
        this.id = id;
        this.startHub = startHub;
        this.route = route;
    }

    public String getId() {
        return id;
    }

    public Hub getStartHub() {
        return startHub;
    }

    public ArrayList<Corridor> getRoute() {
        return route;
    }
}