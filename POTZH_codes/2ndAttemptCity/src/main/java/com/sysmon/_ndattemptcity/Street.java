package com.sysmon._ndattemptcity;

public class Street {

    public int getDurationTimeMs() {
        return durationTimeMs;
    }

    public int getTo() {
        return to;
    }

    public int getFrom() {
        return from;
    }

    public String getStreetName() {
        return streetName;
    }

    private final int durationTimeMs;
    private final  String streetName;
    private final int from;
    private final int to;


    Street(int from, int to, String streetName, int durationTimeInMs){
        this.to = to;
        this.from = from;
        this.streetName = streetName;
        this.durationTimeMs = durationTimeInMs;
    }


    @Override
    public  String toString(){
        return "streetName: " + streetName + " from: " + from + " to: " + to + "duration in s: " +durationTimeMs;
    }

}
