package io.papermc.cinematicbuilder.managers;

import org.bukkit.Location;
import java.util.ArrayList;

public class TeleportLocationsManager {

    private final ArrayList<Location> teleportLocations = new ArrayList<>();
    private final Location originalLocation;
    private final String fileName;
    public TeleportLocationsManager(String name, Location originalLocation) {
        this.originalLocation = originalLocation;
        this.fileName = name;
    }

    public void addNewLocation(Location location) {
        this.teleportLocations.add(location.add(0,1.5,0));
    }

    public Location getOriginalLocation() {
        return this.originalLocation;
    }

    public ArrayList<Location> getTeleportLocations() {
        //Adds the original location as the last location
        teleportLocations.add(originalLocation);

        return teleportLocations;
    }

    public String getFileName() {
        return fileName;
    }
}
