package com.sysmon._ndattemptcity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class City {
    private final List<Car> cars;
    private final Map<String, Street> streets;
    private final  List<Roundabout> roundabouts;
    public static List<Street> routes = new ArrayList<>();



    public List<Roundabout> getRoundabouts() {
        return roundabouts;
    }

    public Map<String, Street> getStreets() {
        return streets;
    }

    public List<Car> getCars() {
        return cars;
    }

    City (List<Roundabout> roundabouts, Map<String,Street> streets, List<Car> cars){
        this.roundabouts = roundabouts;
        this.streets = streets;
        this.cars = cars;
    }

    public void startSimulation(){
        int carCount = cars.size();
        ExecutorService executor = Executors.newFixedThreadPool(carCount);

        //City.startSimulation:
        //    végigmegy a már létező cars listán
        //    minden Car-t külön threaden elindít
        for (Car car: cars){
            car.setCity(this);

            executor.submit(car);

        }
        executor.shutdown();

    }

    public Roundabout getRoundaboutById(int id) {
        for (Roundabout r : roundabouts) {
            if (r.getRoundaboutId() == id) {
                return r;
            }
        }
        return null;
    }

    public String getState(){
       // return "car: " + // Car carid is going (from->to , x s)
    return "Roundabouts: " + roundabouts + "\n\n" +
            "Streets: " + streets + "\n\n"+
            "Cars: " + cars;
    }

}
