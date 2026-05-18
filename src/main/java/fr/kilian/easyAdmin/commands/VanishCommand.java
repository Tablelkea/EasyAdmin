package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.VanishManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VanishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Commande uniquement utilisable en jeu.");
            return true;
        }

        if (!player.hasPermission("easyadmin.vanish")) {
            player.sendMessage(
                    Component.text("Permission insuffisante.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        VanishManager vanishManager =
                Main.getInstance().getVanishManager();

        vanishManager.toggle(player);

        return true;
    }
}