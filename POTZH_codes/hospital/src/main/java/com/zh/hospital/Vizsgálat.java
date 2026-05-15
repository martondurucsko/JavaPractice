package com.zh.hospital;

import java.util.HashMap;
import java.util.Map;

public class Vizsgálat {
    private final String vizsgalatNev;
    private final int testDurationInMs;
    private final int roomId;
    private final Map<String, Vizsgálat> vizsgalatByName = new HashMap<>();


    public Map<String, Vizsgálat> getVizsgalatByName() {
        return vizsgalatByName;
    }

    public String getVizsgalatNev() {
        return vizsgalatNev;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getTestDurationInMs() {
        return testDurationInMs;
    }


    public Vizsgálat(String vizsgalatNev, int testDurationInMs, int roomId) {
        this.vizsgalatNev = vizsgalatNev;
        this.testDurationInMs = testDurationInMs;
        this.roomId = roomId;
    }

    public String getState(){
        return "Visgálat neve: " + this.getVizsgalatNev()
                + ", helye: " + this.getRoomId() + ", ideje: " +this.getTestDurationInMs() +"ms";
    }

}
