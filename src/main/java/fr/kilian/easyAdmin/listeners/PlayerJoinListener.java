package fr.kilian.easyAdmin.listeners;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.models.PlayerProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        PlayerProfile profile = PlayerProfile.getPlayerProfile(event.getPlayer());

        if (profile == null) {
            new PlayerProfile(
                    event.getPlayer().getUniqueId(),
                    event.getPlayer().getName(),
                    new Date()
            );
            return;
        }

        profile.setLastLogin(new Date());

        if (Main.getInstance().getVanishManager().isVanished(event.getPlayer())) {
            Main.getInstance().getVanishManager().enable(event.getPlayer());
        }
    }
}