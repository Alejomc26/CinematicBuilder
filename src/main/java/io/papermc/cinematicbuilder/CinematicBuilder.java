package io.papermc.cinematicbuilder;

import io.papermc.cinematicbuilder.command.CommandCompleter;
import io.papermc.cinematicbuilder.command.CommandListener;
import io.papermc.cinematicbuilder.listener.EventListener;
import io.papermc.cinematicbuilder.managers.CinematicManager;
import io.papermc.cinematicbuilder.managers.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CinematicBuilder extends JavaPlugin {

    private static JavaPlugin instance;
    @Override
    public void onEnable() {
        instance = this;

        this.getDataFolder().mkdirs();

        //Create the player manager
        PlayerManager playerManager = new PlayerManager();
        CinematicManager cinematicManager = new CinematicManager(playerManager);

        //Register the command and pass the player manager
        this.getCommand("cinematicbuilder").setExecutor(new CommandListener(playerManager, cinematicManager));

        //Register the tab completer
        this.getCommand("cinematicbuilder").setTabCompleter(new CommandCompleter());

        //Register the event listener
        this.getServer().getPluginManager().registerEvents(new EventListener(playerManager, cinematicManager), this);
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
