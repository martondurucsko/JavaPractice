package com.sysmon._st_attepmt_korforgalom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityLoader {

    public static City load(String filename)throws IOException {
        List<Roundabout> roundabouts = new ArrayList<>();
        List <Car> cars = new ArrayList<>();
        Map <String, Street> streetbyName = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String firstLine = reader.readLine();

            if (firstLine==null){
                throw new IOException("EMPTY FILE");
            }

            // mi a faszom ez
            String[] firstPart = firstLine.trim().split("\\s+");

            if (firstPart.length<3){
                throw new IOException("FIRST LiNE MUST CONTAINS 3 numbers");
            }

            int roundaboutCount = Integer.parseInt(firstPart[0]);
            int streetCount = Integer.parseInt(firstPart[1]);
            int carCount = Integer.parseInt(firstPart[2]);

            // feltöltjuk a roundbaoutot
            // streetCount
            // carCount

            for (int i = 0; i < roundaboutCount; i++) {
                roundabouts.add(new Roundabout(i));
            }

            for (int i = 0; i < streetCount; i++) {
                String line = reader.readLine();

                if (line == null){
                    throw new IOException("missing streetline");
                }
                // ez itt mi???
                if (line.isBlank()){
                    i--;
                    continue;
                }

                String parts[] = line.trim().split("\\s+");
                if (parts.length < 4) // mindig negy parameter kell
                {
                    throw new IOException("WRONG FORMAT IN CITY");
                }

                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                String streetName = parts[2];
                int timeToPassMs = Integer.parseInt(parts[3]);

                Street street = new Street(from,to,streetName,timeToPassMs);
                streetbyName.put(streetName,street);
            }
            for (int i = 0; i < carCount; i++) {
                String line = reader.readLine();

                if (line == null){
                    throw new IOException("nincs megadva autoutvonal");
                }

                String[] parts = line.trim().split("\\s+");



                // ez most nem tudom miert
                if (line.isBlank()){
                    i--;
                    continue;
                }

                int streetsToGoCount = Integer.parseInt(parts[0]);

                List<Street> route = new ArrayList<>(); // "kossuth, elso , masodik "
                for (int j = 1; j <= streetsToGoCount; j++) {  // 1töl mert az egy szam es innen indulnak a streetek
                    String streetName = parts[j];
                    Street street = streetbyName.get(streetName); // kossuth utca - street objectum hozzá

                    if (street == null) {
                        throw new IOException("Unknown street name in car route: " + streetName);
                    }

                    route.add(street);
                }
                Car car = new Car(i, route,roundabouts);
                cars.add(car);
            }

        }
        return new City(roundabouts,cars,streetbyName);
    }
}
