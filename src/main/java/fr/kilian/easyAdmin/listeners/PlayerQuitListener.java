package fr.kilian.easyAdmin.listeners;

import fr.kilian.easyAdmin.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        if (Main.getInstance()
                .getVanishManager()
                .isVanished(event.getPlayer())) {

            Main.getInstance()
                    .getVanishManager()
                    .disable(event.getPlayer());
        }
    }
}