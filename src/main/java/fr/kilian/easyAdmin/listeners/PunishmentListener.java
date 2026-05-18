package fr.kilian.easyAdmin.listeners;

import fr.kilian.easyAdmin.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.jspecify.annotations.NonNull;

public class PunishmentListener implements Listener {

    @EventHandler
    public void onPreLog(@NonNull AsyncPlayerPreLoginEvent event) {

        if (!Main.getInstance().getPunishmentManager().isBanned(event.getUniqueId())) {
            return;
        }

        event.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
                Component.text("[ADMINISTRATION] Vous êtes banni de ce serveur.")
                        .color(NamedTextColor.RED)
        );
    }
}