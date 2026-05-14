package com.sysmon._st_attepmt_korforgalom;

import java.util.List;
import java.util.Map;

public class City {
    // kell bele egy List - ami kap roundaboutot, streetet
    // kell egy StreetbyName String,Street
    // kell egy car List

    private final List <Roundabout> roundabouts;
    private final List <Car> cars;
    private final Map <String, Street> streetbyName;


    public City(List<Roundabout> roundabouts, List<Car> cars, Map<String, Street> streetbyName) {
        this.roundabouts = roundabouts;
        this.cars = cars;
        this.streetbyName = streetbyName;
    }

    public List<Roundabout> getRoundabouts() {
        return roundabouts;
    }

    public Map<String, Street> getStreetbyName() {
        return streetbyName;
    }

    public List<Car> getCars() {
        return cars;
    }

    //guinak fasza
    public String getInfoText(){
        StringBuilder sb = new StringBuilder();
        sb.append("Roundabouts: \n");
        for (Roundabout r : roundabouts){
            sb.append(r).append("\n");
        }

        sb.append("\n Streets \n");
        // mmikor használok value-t es mikor használok .keysetet
        for (Street street : streetbyName.values()){
            sb.append(street).append("\n");
        }

        for (Car car:cars){
            sb.append(car).append("\n");
        }
        return sb.toString();
    }
}
