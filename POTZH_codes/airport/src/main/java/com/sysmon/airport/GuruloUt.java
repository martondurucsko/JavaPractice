package com.sysmon.airport;

public class GuruloUt {
    private final String guruloUtNev;
    private final int from;
    private final int to;
    private final int travelTimeInMs;


    public int getTravelTimeInMs() {
        return travelTimeInMs;
    }

    public int getTo() {
        return to;
    }

    public int getFrom() {
        return from;
    }

    public String getGuruloUtNev() {
        return guruloUtNev;
    }

    public GuruloUt(String guruloUtNev, int from, int to, int travelTimeInMs) {
        this.from = from;
        this.to = to;
        this.travelTimeInMs = travelTimeInMs;
        this.guruloUtNev = guruloUtNev;
    }







}
