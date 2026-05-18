package fr.kilian.easyAdmin.listeners;

import fr.kilian.easyAdmin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.jspecify.annotations.NonNull;

public class ProtectionListener implements Listener {

    private boolean isFrozen(Player player) {
        return Main.getInstance().getFreezeManager().isFrozen(player);
    }

    @EventHandler
    public void onDrop(@NonNull PlayerDropItemEvent event) {
        if (isFrozen(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(@NonNull BlockBreakEvent event) {
        if (isFrozen(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onDrag(@NonNull InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player player && isFrozen(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void onClick(@NonNull InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && isFrozen(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(@NonNull PlayerInteractEvent event) {
        if (isFrozen(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPickup(@NonNull PlayerPickupItemEvent event) {
        if (isFrozen(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onMove(@NonNull PlayerMoveEvent event) {
        if (!isFrozen(event.getPlayer()))
            return;

        if (event.getFrom().getBlockX() != event.getTo().getBlockX()
                || event.getFrom().getBlockY() != event.getTo().getBlockY()
                || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
            event.setTo(event.getFrom());
        }
    }
}