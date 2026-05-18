package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ModCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Commande uniquement utilisable en jeu.");
            return true;
        }

        if (!player.hasPermission("easyadmin.mod")) {
            player.sendMessage(
                    Component.text("Tu n'as pas la permission.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        Main.getInstance().getModManager().toggleMod(player);

        return true;
    }
}