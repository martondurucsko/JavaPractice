package com.sysmon._st_attepmt_korforgalom;

public class Street {
    private final int from;
    private final int to;

    public String getStreetName() {
        return streetName;
    }

    public int getTo() {
        return to;
    }

    public int getFrom() {
        return from;
    }

    public int getTravelTimeSeconds() {
        return travelTimeSeconds;
    }

    private final String streetName;
    private final int travelTimeSeconds;

    Street (int from, int to, String streetName, int travelTimeSeconds){
        this.from = from;
        this.to = to;
        this.streetName = streetName;
        this.travelTimeSeconds = travelTimeSeconds;
    }



    @Override // azért kell mert alapbol van egy ToStringje és kell hogy legyen egy uj tostringje
    public String toString(){
        return streetName + " (" + from + " -> " + to + ", " + travelTimeSeconds + "s)";
    }

}
