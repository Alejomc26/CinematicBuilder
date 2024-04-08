package io.papermc.cinematicbuilder.parser;

import io.papermc.cinematicbuilder.CinematicBuilder;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StringParser {

    public static void addString(CinematicBuilder main, String string) {
        File file = new File(main.getDataFolder(), "names.yml");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<String> names = config.getStringList("strings");

        names.add(string);

        config.set("strings", names);

        try {
            config.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void removeString(CinematicBuilder main, String string) {
        File file = new File(main.getDataFolder(), "names.yml");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<String> names = config.getStringList("strings");

        names.remove(string);

        config.set("strings", names);

        try {
            config.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String[] deserializeStrings(CinematicBuilder main) {
        File file = new File(main.getDataFolder(), "names.yml");

        if (!file.exists()) {
            return new String[0];
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<String> stringList = config.getStringList("strings");

        return stringList.toArray(new String[0]);
    }
}
