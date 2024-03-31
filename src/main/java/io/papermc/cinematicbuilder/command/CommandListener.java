package io.papermc.cinematicbuilder.command;

import io.papermc.cinematicbuilder.managers.CinematicManager;
import io.papermc.cinematicbuilder.managers.PlayerManager;
import io.papermc.cinematicbuilder.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandListener implements CommandExecutor {

    private final CinematicManager cinematicManager;
    private final PlayerManager manager;
    public CommandListener(PlayerManager manager, CinematicManager cinematicManager) {
        this.cinematicManager = cinematicManager;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return true;
        }

        //Checks if the 2nd argument is equal to new
        if (args[0].equalsIgnoreCase("new")) {

            if (args.length == 2) {
                this.cinematicManager.start(player, args[1]);
                return true;
            }

            player.sendMessage("Write a file name");
        }

        //Checks if the 2nd argument is equal to new
        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length == 2) {
                this.cinematicManager.deleteCinematic(player, args[1]);
            }
        }

        //Checks if the 2nd argument is equal to save
        if (args[0].equalsIgnoreCase("save")) {
            if (manager.isMakingACinematic(player)) {
                //Save the cinematic
                cinematicManager.saveCinematic(player);
            }
        }

        //Checks if the 2nd argument is equal to discard
        if (args[0].equalsIgnoreCase("discard")) {

            //If the player is in the manager stops it
            if (this.manager.isMakingACinematic(player)) {
                this.cinematicManager.discardCinematic(player);
            }
        }

        //Checks if the 2nd argument is equal to play
        if (args[0].equalsIgnoreCase("play")) {

            //Returns if the player is making a cinematic
            if (this.manager.isMakingACinematic(player)) {
                return true;
            }

            if (args.length == 2) {
                this.cinematicManager.play(player, args[1]);
            }

            if (args.length == 3) {
                String playerName = args[2];
                Player playerToPlayCinematic = Bukkit.getPlayer(playerName);
                if (playerToPlayCinematic != null) {
                    this.cinematicManager.play(playerToPlayCinematic, args[1]);
                    Logger.sendMessageToPlayer(player, "You are playing " + args[1] + " to " + playerName);
                } else {
                    Logger.sendMessageToPlayer(player, "Player to play cinematic is null");
                }
            }
        }

        return true;
    }
}
