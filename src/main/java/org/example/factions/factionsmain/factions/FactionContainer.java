package org.example.factions.factionsmain.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactionContainer {
    public static void createFaction(String name, Faction f) {
        factions.put(name, f);
    }

    public static Faction getFaction(String name) {
        return factions.get(name);
    }

    public static void loadFactions() {
        File factionFolder = new File("factions/");
        File[] filesInDir = factionFolder.listFiles();
        List<String> factionPaths = new ArrayList<>();

        if (filesInDir == null) {
            return;
        }
        /* Iterate through faction dir content, remove subdirs and file endings. */
        for (File file : filesInDir) {
            if (file.isFile()) {
                String fileName = file.getName();
                String[] splitFileName = fileName.split("\\.");
                /* If it is a json file load a "faction" from it */
                if (splitFileName[1].equalsIgnoreCase("json")) {
                    factionPaths.add(fileName);
                }
            }
        }

        /* Load the faction files, construct classes from them */
        BufferedReader reader;
        Gson gsonInstance = new Gson();
        for (String s : factionPaths) {
            try {
                reader = new BufferedReader(new FileReader("factions/" + s));
            } catch (FileNotFoundException e) {
                return;
            }
            Faction f = gsonInstance.fromJson(reader, Faction.class);
            factions.put(s.split("\\.")[0], f);
        }
    }

    public static void saveFactions() {
        Gson gsonInstance = new Gson();
        FileWriter writer;
        String filePath = "";

        /* Check if directory exists, if not create it. */
        String directoryName = "factions/";
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        /* Convert Faction Instances to a JSON string and save them. */
        for (HashMap.Entry<String, Faction> m: factions.entrySet()) {
            String factionAsJsonString = gsonInstance.toJson(m.getValue());
            try {
                filePath = "factions/" + m.getKey() + ".json";
                writer = new FileWriter(filePath);
            } catch (IOException e) {
                logger.error("Could not save faction: " + m.getValue());
                logger.error("There was an I/O Exception opening: " + filePath);
                return;
            }
            try {
                writer.write(factionAsJsonString);
            } catch (IOException e) {
                logger.error("Could not save faction: " + m.getValue());
                logger.error("There was an I/O Exception writing: " + filePath);
                return;
            }
            try {
                writer.close();
            } catch (IOException e) {
                logger.error("Could not save faction: " + m.getValue());
                logger.error("There was an I/O Exception closing: " + filePath);
                return;
            }
        }
    }

    private static HashMap<String, Faction> factions = new HashMap<String, Faction>();

    @Inject
    private static Logger logger;
}
