package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.PunishmentManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static fr.kilian.easyAdmin.utils.MessagesFormats.*;

public class UnmuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof Player player &&
                !player.hasPermission("easyadmin.unmute")) {
            sender.sendMessage(
                    Component.text(DONT_HAVE_PERMISSION.getMessage())
            );
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(
                    Component.text(UNMUTE_HELP.getMessage())
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

        PunishmentManager punishmentManager =
                Main.getInstance().getPunishmentManager();

        if (!punishmentManager.isMuted(target.getUniqueId())) {
            sender.sendMessage(
                    Component.text(PLAYER_NOT_MUTE.getMessage())
            );
            return true;
        }

        if (sender instanceof Player player) {
            punishmentManager.unmute(player, target);
        } else {
            punishmentManager.unmute(target);
        }



        return true;
    }
}