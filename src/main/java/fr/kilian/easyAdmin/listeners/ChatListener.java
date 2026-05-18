package fr.kilian.easyAdmin.listeners;

import fr.kilian.easyAdmin.Main;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jspecify.annotations.NonNull;

import static fr.kilian.easyAdmin.utils.MessagesFormats.MUTE_MESSAGE;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(@NonNull AsyncChatEvent event) {

        if (!Main.getInstance()
                .getPunishmentManager()
                .isMuted(event.getPlayer().getUniqueId())) {
            return;
        }

        event.setCancelled(true);

        event.getPlayer().sendMessage(
                Component.text(
                        MUTE_MESSAGE.getMessage()
                )
        );
    }
}