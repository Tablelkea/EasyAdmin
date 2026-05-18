package fr.kilian.easyAdmin.listeners;

import fr.kilian.easyAdmin.Main;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jspecify.annotations.NonNull;

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
                        "[ADMINISTRATION] Vous êtes actuellement réduit au silence."
                ).color(NamedTextColor.RED)
        );
    }
}