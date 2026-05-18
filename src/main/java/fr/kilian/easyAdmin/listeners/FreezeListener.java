package fr.kilian.easyAdmin.listeners;

import fr.kilian.easyAdmin.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jspecify.annotations.NonNull;

public class FreezeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMove(@NonNull PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!Main.getInstance().getFreezeManager().isFrozen(player)) return;

        // Autoriser la rotation de la tête uniquement (même X/Y/Z)
        if (event.getFrom().getBlockX() != event.getTo().getBlockX()
                || event.getFrom().getBlockY() != event.getTo().getBlockY()
                || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
            event.setCancelled(true);
            player.sendMessage(Component.text("Vous êtes gelé, vous ne pouvez pas bouger.", NamedTextColor.AQUA));
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onTeleport(@NonNull PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (!Main.getInstance().getFreezeManager().isFrozen(player)) return;

        if (event.getCause() != PlayerTeleportEvent.TeleportCause.PLUGIN
                && event.getCause() != PlayerTeleportEvent.TeleportCause.COMMAND) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(@NonNull PlayerQuitEvent event) {
        Main.getInstance().getFreezeManager().unfreeze(null, event.getPlayer());
    }
}