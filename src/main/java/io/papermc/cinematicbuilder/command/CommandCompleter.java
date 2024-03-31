package io.papermc.cinematicbuilder.command;

import io.papermc.cinematicbuilder.parser.StringParser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> subCommands = new ArrayList<>();
        if (args.length == 1) {
            subCommands.add("new");
            subCommands.add("delete");
            subCommands.add("discard");
            subCommands.add("save");
            subCommands.add("play");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("play")) {
                String[] names = StringParser.deserializeStrings();
                subCommands.addAll(Arrays.asList(names));
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("play")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    subCommands.add(player.getName());
                }
            }
        }
        return subCommands;
    }
}
