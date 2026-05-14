package com.sysmon._st_attepmt_korforgalom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulationManager {
    private City city;
    private final List<Thread> carThreads = new ArrayList<>();

    public void load(String filename) throws IOException {
        city = CityLoader.load(filename);
    }

    // gui miatt csak egy hivas

    public void startSimulation() {
        if (city == null) {
            throw new IllegalStateException("No city loaded");
        }

        carThreads.clear();

        for (Car car : city.getCars()) {
            Thread t = new Thread(car);
            carThreads.add(t);
            t.start();
        }
    }

    public void stopSimulation() {
        for (Thread t : carThreads) {
            t.interrupt();
        }
    }

    public String getStateText() {
        if (city == null) {
            return "No file loaded";
        }

        return city.getInfoText();
    }
}


