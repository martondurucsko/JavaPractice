package com.zh.cargohub;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class ScenarioLoader {

    public static Scenario load(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));

        String firstLine = reader.readLine();
        String[] firstParts = firstLine.trim().split("\\s+");

        int hubCount = Integer.parseInt(firstParts[0]);
        int corridorCount = Integer.parseInt(firstParts[1]);
        int botCount = Integer.parseInt(firstParts[2]);

        HashMap<String, Hub> hubs = new HashMap<>();
        HashMap<String, Corridor> corridors = new HashMap<>();
        ArrayList<BotSpec> bots = new ArrayList<>();

        for (int i = 0; i < hubCount; i++) {
            String line = reader.readLine();
            String[] parts = line.trim().split("\\s+");

            String hubId = parts[0];
            int capacity = Integer.parseInt(parts[1]);
            long crossingTime = Long.parseLong(parts[2]);

            Hub hub = new Hub(hubId, capacity, crossingTime);
            hubs.put(hubId, hub);
        }

        for (int i = 0; i < corridorCount; i++) {
            String line = reader.readLine();
            String[] parts = line.trim().split("\\s+");

            String fromId = parts[0];
            String toId = parts[1];
            String corridorName = parts[2];
            long travelTime = Long.parseLong(parts[3]);

            Hub from = hubs.get(fromId);
            Hub to = hubs.get(toId);

            Corridor corridor = new Corridor(corridorName, from, to, travelTime);
            corridors.put(corridorName, corridor);
        }

        for (int i = 0; i < botCount; i++) {
            String line = reader.readLine();
            String[] parts = line.trim().split("\\s+");

            String botId = parts[0];
            String startHubId = parts[1];
            int routeLength = Integer.parseInt(parts[2]);

            Hub startHub = hubs.get(startHubId);
            ArrayList<Corridor> route = new ArrayList<>();

            for (int j = 0; j < routeLength; j++) {
                String corridorName = parts[3 + j];
                Corridor corridor = corridors.get(corridorName);
                route.add(corridor);
            }

            BotSpec bot = new BotSpec(botId, startHub, route);
            bots.add(bot);
        }

        reader.close();

        return new Scenario(path.toString(), hubs, corridors, bots);
    }
}