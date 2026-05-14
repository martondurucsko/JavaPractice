package com.sysmon.airport;

// megcsinálja az adatStrukturát -  filebol


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirPortLoader {
    private final List<GuruloUt> route = new ArrayList<>();
    private final Map<Integer, CsomoPont> csomoPontById = new HashMap<>(); //by id nem NAME
    private final List<Thread> planeThreads = new ArrayList<>();
    private final Map<String, GuruloUt> guruloUtByName = new HashMap<>();
    // nem tudom miert az ai mondta...
    private final List<String> logs = new ArrayList<>();


    public Airport load(String file) {
        List<Plane> planes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            String[] firstParts = line.trim().split("\\s+");

            int allCsomopontCount = Integer.parseInt(firstParts[0]);
            int allGuruloUtCount = Integer.parseInt(firstParts[1]);
            int allPlaneCount = Integer.parseInt(firstParts[2]);

            // Csomopont

            for (int i = 0; i < allCsomopontCount; i++) {
                String csomopontLine = reader.readLine();
                String[] csomopontParts = csomopontLine.trim().split("\\s+");

                int csomopontId = Integer.parseInt(csomopontParts[0]);
                String csomopontType = csomopontParts[1];

                CsomoPont csomoPont = new CsomoPont(csomopontId, csomopontType);
                csomoPontById.put(csomopontId, csomoPont);
            }


            // gurulout

            for (int i = 0; i < allGuruloUtCount; i++) {
                String guruloUtLine = reader.readLine();
                String[] guruloutParts = guruloUtLine.trim().split("\\s+");

                String guruloUtNev = guruloutParts[0];
                int from = Integer.parseInt(guruloutParts[1]);
                int to = Integer.parseInt(guruloutParts[2]);
                int travelTimeinMs = Integer.parseInt(guruloutParts[3]);

                GuruloUt guruloUt = new GuruloUt(guruloUtNev, from, to, travelTimeinMs);
                guruloUtByName.put(guruloUtNev, guruloUt);
            }

            // plane

            for (int i = 1; i <= allPlaneCount; i++) {
                String planeLines = reader.readLine();
                String[] planeParts = planeLines.trim().split("\\s+");

                String planename = planeParts[0];
                int routelenght = Integer.parseInt(planeParts[1]);

                List<GuruloUt> route = new ArrayList<>();

                for (int j = 0; j < routelenght; j++) {
                    String GuruloUtName = planeParts[2 + j];
                    GuruloUt guruloUt = guruloUtByName.get(GuruloUtName);
                    route.add(guruloUt);
                }
                // ciklus utan adjuk hozza
                Plane plane = new Plane(planename, route);
                planes.add(plane); // emiatt kell a 23. sor


            }

            return new Airport(csomoPontById, guruloUtByName, planes); //nagyon fontos beloadolni az airportot

        } catch (Exception e) {
            System.out.println("BEOLVASAS FAIL" + e);
        }
        return null;
    }


}
