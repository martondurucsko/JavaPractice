package com.sysmon._ndattemptcity;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CityLoader {

    public static File inputFile;
    public static List<Roundabout> roundabouts = new ArrayList<>();
    public static Map<String,Street> streetByName = new HashMap<>();
    public static List<Street> routes = new ArrayList<>();
    public static List<Car> cars = new ArrayList<>();


    public static City load(String fileName) throws IOException{

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName));) {
            String firstLine = reader.readLine();
            String[] firstParts = firstLine.trim().split("\\s+");
            if (firstParts.length<3){
                throw new IOException("City metrics are not correct must be 3 digits");
            }
            int countAllRoundabouts = Integer.parseInt(firstParts[0]);
            int countAllStreets = Integer.parseInt(firstParts[1]);
            int countAllCars = Integer.parseInt(firstParts[2]);


            //roundaboutok létrehozása
            for (int i = 0; i < countAllRoundabouts; i++) {
                roundabouts.add(new Roundabout(i));
            }




            // ezt egy pár sorral később
            for (int i = 0; i < countAllStreets; i++) {
                String streetLine = reader.readLine();
                String[] streetParts = streetLine.trim().split("\\s+"); // from to name ido
                 
                int durationTimeMs = Integer.parseInt(streetParts[3]);;
                 String streetName = streetParts[2];
                 int from = Integer.parseInt(streetParts[0]);;
                 int to = Integer.parseInt(streetParts[1]);

                // kene egy exception hogy ha kevesebb line van mint mint ameddig tart a count allstreet akkor exception

                Street street = new Street(from,to,streetName,durationTimeMs);
                
                streetByName.put(streetName,street);

            }

            //kocsit is
            for (int i = 0; i < countAllCars; i++) {
                String carLine = reader.readLine();
                String[] carRouteParts = carLine.trim().split("\\s+");
                routes = new ArrayList<>();
                for (int j = 1; j < carRouteParts.length; j++) {
                    String streetNameStop = carRouteParts[j];
                    Street actStreet = streetByName.get(streetNameStop);
                    routes.add(actStreet);
                }
                Car car = new Car(i, routes);
                cars.add(car);                                       /// ezt nem szabad elfelejteni
            }

        }
        catch (IOException e){
            System.out.println("File not found");
        }

        return new City(roundabouts,streetByName,cars);
    }
    public static void main(String[] args) throws IOException {
        inputFile = new File("korforgalmak_1.txt");

            City city = CityLoader.load("korforgalmak_1.txt");  /// kell egy példány
            System.out.println(city.getState());



    }




}
