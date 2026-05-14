package com.zh.cargohub;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Scenario {

    private final String name;
    private final Map<String, Hub> hubs;
    private final Map<String, Corridor> corridors;
    private final List<BotSpec> bots;

    public Scenario(String name,
                    Map<String, Hub> hubs,
                    Map<String, Corridor> corridors,
                    List<BotSpec> bots) {

        this.name = name;
        this.hubs = new LinkedHashMap<>(hubs);
        this.corridors = new LinkedHashMap<>(corridors);
        this.bots = List.copyOf(bots);
    }

    public String getName() {
        return name;
    }

    public Map<String, Hub> getHubs() {
        return Collections.unmodifiableMap(hubs);
    }

    public Map<String, Corridor> getCorridors() {
        return Collections.unmodifiableMap(corridors);
    }

    public List<BotSpec> getBots() {
        return Collections.unmodifiableList(bots);
    }

    public Hub getHub(String id) {
        return hubs.get(id);
    }

    public Corridor getCorridor(String name) {
        return corridors.get(name);
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "name='" + name + '\'' +
                ", hubs=" + hubs.size() +
                ", corridors=" + corridors.size() +
                ", bots=" + bots.size() +
                '}';
    }
}
