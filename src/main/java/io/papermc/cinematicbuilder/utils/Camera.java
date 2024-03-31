package io.papermc.cinematicbuilder.utils;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

public final class Camera {

    private final ItemDisplay camera;
    public Camera(Location location) {
        this.camera = location.getWorld().spawn(location, ItemDisplay.class);
        this.camera.setTeleportDuration(1);
    }

    public void teleport(Location location) {
        this.camera.teleport(location);
    }

    public void remove() {
        this.camera.remove();
    }

    public void spectateCamera(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        player.setSpectatorTarget(camera);
    }
}
