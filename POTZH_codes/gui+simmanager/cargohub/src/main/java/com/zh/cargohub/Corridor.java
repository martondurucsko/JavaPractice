package com.zh.cargohub;

public class Corridor {

    private String name;
    private Hub from;
    private Hub to;
    private long travelTimeMillis;

    public Corridor(String name, Hub from, Hub to, long travelTimeMillis) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.travelTimeMillis = travelTimeMillis;
    }

    public String getName() {
        return name;
    }

    public Hub getFrom() {
        return from;
    }

    public Hub getTo() {
        return to;
    }

    public long getTravelTimeMillis() {
        return travelTimeMillis;
    }
}