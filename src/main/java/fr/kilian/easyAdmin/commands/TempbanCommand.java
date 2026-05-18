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

import java.time.Duration;
import java.util.Arrays;

import static fr.kilian.easyAdmin.utils.MessagesFormats.*;

public class TempbanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof Player player &&
                !player.hasPermission("easyadmin.tempban")) {
            sender.sendMessage(
                    Component.text(DONT_HAVE_PERMISSION.getMessage())
            );
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(
                    Component.text(TEMP_BAN_HELP.getMessage())
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

        if (sender instanceof Player player && target.equals(player)) {
            sender.sendMessage(
                    Component.text(CANNOT_SELF_TEMPBAN.getMessage())
            );
            return true;
        }

        long duration;

        try {
            duration = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(
                    Component.text(BAD_TIME_ARGUMENT.getMessage())
            );
            return true;
        }

        String reason = String.join(" ",
                Arrays.copyOfRange(args, 2, args.length));

        PunishmentManager punishmentManager =
                Main.getInstance().getPunishmentManager();

        if (punishmentManager.isBanned(target.getUniqueId())) {
            sender.sendMessage(
                    Component.text(PLAYER_ALREADY_TEMPBAN.getMessage())
            );
            return true;
        }

        Player mod = sender instanceof Player p ? p : target;

        punishmentManager.tempBan(
                mod,
                target,
                Duration.ofDays(duration),
                reason
        );



        return true;
    }
}