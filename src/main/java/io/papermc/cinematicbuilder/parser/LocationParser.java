package io.papermc.cinematicbuilder.parser;

import io.papermc.cinematicbuilder.CinematicBuilder;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationParser {

    public static void serializeLocations(ArrayList<Location> locations, String fileName) {

        //Create file that will contain the locations
        File file = new File(CinematicBuilder.getInstance().getDataFolder(), fileName + ".yml");

        //Create yaml config
        YamlConfiguration config = new YamlConfiguration();

        //Write the locations in the yaml config
        config.set(fileName, locations);

        try {
            //Save the yaml config inside file
            config.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public static Location[] deserializeLocations(String fileName) {

        //Gets the file to deserialize
        File file = new File(CinematicBuilder.getInstance().getDataFolder(), fileName + ".yml");

        //Creates the list that will contain all the locations
        List<Location> locations = new ArrayList<>();

        //Loads the configuration of the file into a yaml
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        //Reads the yaml and gets the list of locations
        List<?> locationList = config.getList(fileName);

        //Write the list of locations to locations
        if (locationList != null) {
            for (Object object : locationList) {
                if (object instanceof Location) {
                    locations.add((Location) object);
                }
            }
        }

        return locations.toArray(new Location[0]);
    }
}
