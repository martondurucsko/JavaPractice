package com.sysmon._st_attepmt_korforgalom;

import java.util.List;

public class Car implements Runnable{
    private final int carId;
    // private final int streetCount; ez sem kell mert van egy Street osztály
    private final List<Street> route;
    private volatile boolean running=true;
    private final List <Roundabout> roundabouts;


    public int getCarId() {
        return carId;
    }

    public List<Street> getRoute() {
        return route;
    }

    public Car(int carId, List<Street> route,List <Roundabout> roundabouts) {
        this.carId = carId;
        this.route = route;
        this.roundabouts = roundabouts;
    }


    @Override
    public String toString(){
        return "Car " + carId + "route: " +route;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < route.size(); i++) {
                if (!running){
                    return;
                }
                Street currentStreet = route.get(i);

                System.out.println("car " + carId + "is running on: " + currentStreet.getStreetName());

                Thread.sleep(currentStreet.getTravelTimeSeconds()*1000);

                if (i < route.size()-1){
                    Street nextStreet = route.get(i+1);

                    int roundaboutId = findCommonRoundabout(currentStreet,nextStreet);
                    Roundabout roundabout = roundabouts.get(roundaboutId);

                    roundabout.cross(carId);
                }

            }
            System.out.println("car " + carId + "finished route");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public void stop(){
         running = false;
    }

    private int findCommonRoundabout(Street a, Street b){
        if (a.getFrom() == b.getTo() || a.getFrom() == b.getFrom()){
            return a.getFrom();
        }
        if (b.getFrom() == a.getTo() || a.getTo() == b.getTo()){
            return a.getTo();
        }

        throw new IllegalStateException("No common roundabout between " + a.getStreetName() + " and " + b.getStreetName());
    }
}
