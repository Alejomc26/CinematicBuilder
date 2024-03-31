package io.papermc.cinematicbuilder.managers;

import java.util.ArrayList;
import java.util.List;

public class CinematicNamesManager {

    private final List<String> names = new ArrayList<>();

    public void addName(String name) {
        this.names.add(name);
    }

    public void removeName(String name) {
        this.names.remove(name);
    }

    public String[] getNames() {
        return this.names.toArray(new String[0]);
    }
}
