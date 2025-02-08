package org.example.factions.factionsmain.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.example.factions.factionsmain.utils.JsonLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactionContainer {
    public static void loadFactions() {
        factions = new JsonLoader().load("factions/", Faction.class);
    }

    public static void saveFactions() {
        new JsonLoader().save(factions, "factions/");
    }

    public static void createFaction(String name, Faction f) {
        factions.put(name, f);
    }

    public static Faction getFaction(String name) {
        return factions.get(name);
    }

    private static HashMap<String, Faction> factions = new HashMap<String, Faction>();

    @Inject
    private static Logger logger;
}
