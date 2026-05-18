package fr.kilian.easyAdmin.listeners;

import fr.kilian.easyAdmin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jspecify.annotations.NonNull;

public class VanishListener implements Listener {

    @EventHandler
    public void onTarget(@NonNull EntityTargetEvent event) {

        if (event.getTarget() == null) {
            return;
        }

        if (!(event.getTarget() instanceof Player target)) {
            return;
        }

        if (Main.getInstance()
                .getVanishManager()
                .isVanished(target)) {

            event.setCancelled(true);
        }
    }
}