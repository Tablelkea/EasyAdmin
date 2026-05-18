package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.FreezeManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static fr.kilian.easyAdmin.utils.MessagesFormats.*;

public class UnfreezeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof Player player &&
                !player.hasPermission("easyadmin.unfreeze")) {
            sender.sendMessage(
                    Component.text(DONT_HAVE_PERMISSION.getMessage())
            );
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(
                    Component.text(UNFREEZE_HELP.getMessage())
            );
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(
                    Component.text(PLAYER_NOT_FOUND.getMessage())
            );
            return true;
        }

        FreezeManager freezeManager =
                Main.getInstance().getFreezeManager();

        if (!freezeManager.isFrozen(target)) {
            sender.sendMessage(
                    Component.text(PLAYER_NOT_FREEZE.getMessage())
            );
            return true;
        }

        if (sender instanceof Player player) {
            freezeManager.unfreeze(player, target);
        } else {
            // utilise la surcharge console-safe de ton manager
            freezeManager.unfreeze(target);
        }

        return true;
    }
}