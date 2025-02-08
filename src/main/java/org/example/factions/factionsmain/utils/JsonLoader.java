package org.example.factions.factionsmain.utils;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

public class JsonLoader {
    public <T> HashMap<String, T> load(String folderName, Class<T> clazz) {
        File folder = new File(folderName);
        File[] filesInDir = folder.listFiles();
        List<String> paths = new ArrayList<>();
        HashMap<String, T> returnElements = new HashMap<>();

        if (filesInDir == null) {
            return returnElements;
        }
        /* Iterate through faction dir content, remove subdirs and file endings. */
        for (File file : filesInDir) {
            if (file.isFile()) {
                String fileName = file.getName();
                String[] splitFileName = fileName.split("\\.");
                /* If it is a json file load a "faction" from it */
                if (splitFileName[1].equalsIgnoreCase("json")) {
                    paths.add(fileName);
                }
            }
        }

        /* Load the faction files, construct classes from them */
        BufferedReader reader;
        Gson gsonInstance = new Gson();
        for (String s : paths) {
            try {
                reader = new BufferedReader(new FileReader(folderName + s));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            T element = (T) gsonInstance.fromJson(reader, clazz);
            returnElements.put(s.split("\\.")[0], element);
        }
        return returnElements;
    }

    public <T> void save(HashMap<String, T> elems, String folderName) {
        Gson gsonInstance = new Gson();
        FileWriter writer;
        String filePath = "";

        /* Check if directory exists, if not create it. */
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        /* Convert Faction Instances to a JSON string and save them. */
        for (HashMap.Entry<String, T> m : elems.entrySet()) {
            String factionAsJsonString = gsonInstance.toJson(m.getValue());
            try {
                filePath = folderName + m.getKey() + ".json";
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
    @Inject
    private static Logger logger;
}