package fr.kilian.easyAdmin.managers;

import fr.kilian.easyAdmin.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

public class LogManager {

    public void log(@NonNull String message) {
        Main.getInstance().getServer().getConsoleSender().sendMessage(Component.text(message));
    }

    public void logPunishment(@NonNull Player mod, @NonNull Player target, @NonNull String action) {
        Main.getInstance().getServer().getConsoleSender().sendMessage(
                Component.text("[SANCTION] MODERATEUR: " + mod.getName() + " | JOUEUR: " + target.getName() + " | ACTION: " + action)
        );
    }

    public void logModToggle(@NonNull Player player, boolean enabled) {
        Main.getInstance().getServer().getConsoleSender().sendMessage(
                Component.text("[MOD] MODERATEUR: " + player.getName() + " | ACTIF: " + enabled)
        );
    }

    public void sendDiscord(@NonNull String message) {
        // à implémenter
    }

}