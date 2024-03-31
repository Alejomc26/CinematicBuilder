package io.papermc.cinematicbuilder.managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private final HashMap<UUID, TeleportLocationsManager> makingCinematic = new HashMap<>();

    public void addPlayer(Player player, TeleportLocationsManager manager) {
        this.makingCinematic.put(player.getUniqueId(), manager);
    }

    public void removePlayer(Player player) {
        this.makingCinematic.remove(player.getUniqueId());
    }

    public TeleportLocationsManager getPlayerTeleportManager(Player player) {
        return this.makingCinematic.get(player.getUniqueId());
    }

    public boolean isMakingACinematic(Player player) {
        return this.makingCinematic.containsKey(player.getUniqueId());
    }

}
