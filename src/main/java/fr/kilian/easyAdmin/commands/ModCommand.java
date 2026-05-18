package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static fr.kilian.easyAdmin.utils.MessagesFormats.AVAILABLE_ONLY_INGAME;
import static fr.kilian.easyAdmin.utils.MessagesFormats.DONT_HAVE_PERMISSION;

public class ModCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(AVAILABLE_ONLY_INGAME.getMessage());
            return true;
        }

        if (!player.hasPermission("easyadmin.mod")) {
            player.sendMessage(
                    Component.text(DONT_HAVE_PERMISSION.getMessage())
            );
            return true;
        }

        Main.getInstance().getModManager().toggleMod(player);

        return true;
    }
}