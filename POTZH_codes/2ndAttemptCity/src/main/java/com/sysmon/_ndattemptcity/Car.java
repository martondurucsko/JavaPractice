package com.sysmon._ndattemptcity;

import java.util.ArrayList;
import java.util.List;

public class Car implements Runnable{
    public  int getCarId() {
        return carId;
    }

    public List<Street> getRoute() {
        return route;
    }

    public final int carId;
    public final List <Street> route;
    private City city;

    public void setCity(City city) {
        this.city = city;
    }

    Car(int carId, List<Street> route ){
        this.carId = carId;
        this.route = route;
    }



    @Override
    public String toString(){
        return "Car " + carId + "route is: " + route;
    }

    public int commonRoundabout(Street a, Street b){
        if (a.getFrom()==b.getTo() || a.getFrom()==b.getFrom()){
            return a.getFrom();
        }
        if (a.getTo() == b.getFrom() || a.getTo() == b.getTo()){
            return a.getTo();
        }
        return -1;

    }


    @Override
    public void run() {
        try {
        System.out.println("Car "+ carId + " started");
        System.out.println("Car "+ carId + " route: " + getRoute());

        for (int i = 0; i < route.size(); i++) {
            Street currentStreet = route.get(i);
            System.out.println("Car " + carId + " is driving on " + currentStreet.getStreetName());

                Thread.sleep(currentStreet.getDurationTimeMs()*1000);

                if (i<route.size()-1){
                    Street nextStreet = route.get(i+1);

                    int roundaboutId = commonRoundabout(currentStreet,nextStreet);

                    Roundabout roundabout = city.getRoundaboutById(roundaboutId);
                    System.out.println("Car " + carId + "is arrived at roundabout" + roundaboutId);


                    roundabout.enteringRoundabout(this);

                    System.out.println("Car " + carId + " entered roundabout " + roundaboutId);

                    Thread.sleep(1000); // ennyit van benne

                    roundabout.leavingRoundabout(this);

                }
        }
        System.out.println("car " + carId + "is finshed its route");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // az élete / protocol

        // végig megy  routeon commonroundaboutot nez amelyik az arra bemegy
    }
}
