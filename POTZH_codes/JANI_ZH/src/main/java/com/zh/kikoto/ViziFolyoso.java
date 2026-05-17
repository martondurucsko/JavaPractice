package com.zh.kikoto;

public class ViziFolyoso {

    private final int endPoint;
    private final String viziFolyosoNev;

    public int getStartPoint() {
        return startPoint;
    }

    public String getViziFolyosoNev() {
        return viziFolyosoNev;
    }

    public int getTravelTimeInMs() {
        return travelTimeInMs;
    }

    public int getEndPoint() {
        return endPoint;
    }

    private final int startPoint;
    private final int travelTimeInMs;
    private Kikoto kikoto;
    private KikotoManager manager;

    public ViziFolyoso(int startPoint, int endPoint, String viziFolyosoNev, int travelTimeInMs) {
        this.viziFolyosoNev=viziFolyosoNev;
        this.startPoint=startPoint;
        this.endPoint = endPoint;
        this.travelTimeInMs = travelTimeInMs;

        //lehet ossze kell kapcsolni
    }


    public void setKikotoManager (KikotoManager manager){
        this.manager = manager;
    }

}
