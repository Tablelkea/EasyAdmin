package fr.kilian.easyAdmin.managers;

import fr.kilian.easyAdmin.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static fr.kilian.easyAdmin.utils.MessagesFormats.*;

public class VanishManager {

    private final Map<UUID, BukkitTask> vanishedPlayers = new HashMap<>();

    public void enable(@NonNull Player player) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                Main.getInstance(),
                () -> player.sendActionBar(Component.text(IN_VANISH.getMessage())),
                0L,
                40L
        );
        vanishedPlayers.put(player.getUniqueId(), task);
        player.sendMessage(Component.text(SWITCH_TO_VANISH.getMessage()));
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.hidePlayer(Main.getInstance(), player);
        }
        player.displayName(Component.text("[VANISH] ").color(NamedTextColor.RED)
                .append(Component.text(player.getName()).color(NamedTextColor.GOLD)));
    }

    public void disable(@NonNull Player player) {
        BukkitTask task = vanishedPlayers.remove(player.getUniqueId());
        if (task != null) task.cancel();
        player.sendMessage(Component.text(DISABLE_VANISH.getMessage()));
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.showPlayer(Main.getInstance(), player);
        }
    }

    public void toggle(@NonNull Player player) {
        if (isVanished(player))
            disable(player);
        else
            enable(player);
    }

    public boolean isVanished(@NonNull Player player) {
        return vanishedPlayers.containsKey(player.getUniqueId());
    }

}