package io.papermc.cinematicbuilder.listener;

import io.papermc.cinematicbuilder.managers.CinematicManager;
import io.papermc.cinematicbuilder.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {

    private final PlayerManager manager;
    private final CinematicManager cinematicManager;
    public EventListener(PlayerManager manager, CinematicManager cinematicManager) {
        this.cinematicManager = cinematicManager;
        this.manager = manager;
    }

    @EventHandler
    public void disconnectListener(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (this.manager.isMakingACinematic(player)) {
            cinematicManager.discardCinematic(player);
        }
    }
}
