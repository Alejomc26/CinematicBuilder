package io.papermc.cinematicbuilder.managers;

import io.papermc.cinematicbuilder.CinematicBuilder;
import io.papermc.cinematicbuilder.parser.LocationParser;
import io.papermc.cinematicbuilder.parser.StringParser;
import io.papermc.cinematicbuilder.utils.Camera;
import io.papermc.cinematicbuilder.utils.Logger;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class CinematicManager {

    private final PlayerManager manager;
    private final CinematicBuilder main;
    public CinematicManager(CinematicBuilder main, PlayerManager manager) {
        this.manager = manager;
        this.main = main;
    }

    public void start(Player player, String fileName) {
        TeleportLocationsManager teleportLocationsManager = new TeleportLocationsManager(fileName, player.getLocation());
        this.manager.addPlayer(player, teleportLocationsManager);

        Logger.sendMessageToPlayer(player, "Making new cinematic named " + fileName);
        player.setGameMode(GameMode.SPECTATOR);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isValid() && manager.isMakingACinematic(player)) {
                    Location playerLocation = player.getLocation();
                    manager.getPlayerTeleportManager(player).addNewLocation(playerLocation);
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(main, 0, 1);
    }

    public void deleteCinematic(Player player, String fileName) {
        File file = new File(main.getDataFolder(), fileName + ".yml");
        StringParser.removeString(main, fileName);

        if (file.delete()) {
            Logger.sendMessageToPlayer(player, fileName + " deleted successfully");
        } else {
            Logger.sendMessageToPlayer(player, "Couldn't delete " + fileName);
        }
    }

    public void saveCinematic(Player player) {

        //Gets the teleport manager of the player
        TeleportLocationsManager teleportManager = this.manager.getPlayerTeleportManager(player);

        //Teleports the player to its original location
        player.teleport(teleportManager.getOriginalLocation());

        //Save the locations in a file
        LocationParser.serializeLocations(main, teleportManager.getTeleportLocations(), teleportManager.getFileName());

        //Add the file to the command completer
        StringParser.addString(main, teleportManager.getFileName());

        //Remove the player from the manager
        this.manager.removePlayer(player);

        Logger.sendMessageToPlayer(player, "Stopped cinematic");
        player.setGameMode(GameMode.SURVIVAL);
    }

    public void discardCinematic(Player player) {
        TeleportLocationsManager teleportManager = this.manager.getPlayerTeleportManager(player);

        player.teleport(teleportManager.getOriginalLocation());

        this.manager.removePlayer(player);


        Logger.sendMessageToPlayer(player, "Cinematic discarded");
        player.setGameMode(GameMode.SURVIVAL);
    }

    public void play(Player player, String cinematicName) {
        //Deserialize the locations into a list of locations
        Location[] locations = LocationParser.deserializeLocations(main, cinematicName);

        //Creates the camera and makes the player spectate it
        Camera camera = new Camera(player.getLocation());
        camera.spectateCamera(player);

        new BukkitRunnable() {
            int currentLocationNumber;

            @Override
            public void run() {
                //If player is no longer valid stops the thing
                if (!player.isValid()) {
                    manager.removePlayer(player);
                    camera.remove();
                    this.cancel();
                    return;
                }

                //After it iterates the locations stops the thing
                if (currentLocationNumber >= locations.length) {
                    player.setGameMode(GameMode.SURVIVAL);
                    manager.removePlayer(player);
                    camera.remove();
                    this.cancel();
                    return;
                }

                //Teleports the camera to the next location
                camera.teleport(locations[currentLocationNumber++]);
            }
        }.runTaskTimer(main, 0, 1);
    }
}
