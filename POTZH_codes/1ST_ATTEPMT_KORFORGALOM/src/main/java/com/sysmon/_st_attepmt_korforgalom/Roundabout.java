package com.sysmon._st_attepmt_korforgalom;

public class Roundabout {
    public Roundabout(int roundaboutId) {
        this.roundaboutId = roundaboutId;
    }

    public int getRoundaboutId() {
        return roundaboutId;
    }

    private final int roundaboutId;


    @Override
    public String toString(){
        return "Roundabout " + roundaboutId;
    }
    private final Object lock = new Object();
    public void cross(int carId) throws InterruptedException {
        synchronized (lock){
            System.out.println("Car " + carId + "entered the roundabout");
            Thread.sleep(1000);
            System.out.println("Car " + carId + "left the roundabout");
        }
    }
}
