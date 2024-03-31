package io.papermc.cinematicbuilder.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class Logger {

    public static void sendMessageToPlayer(Player player, String message) {
        final Component text = Component.text()
                .content("<CinematicBuilder> ").color(TextColor.color(13,136,209))
                .append(Component.text(message, TextColor.color(204,224,255)))
                .build();

        player.sendMessage(text);
    }
}
