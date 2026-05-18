package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.PunishmentManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Arrays;

public class MuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof Player player &&
                !player.hasPermission("easyadmin.mute")) {
            sender.sendMessage(
                    Component.text("Permission insuffisante.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(
                    Component.text("Usage: /mute <joueur> <minutes> <raison>")
                            .color(NamedTextColor.YELLOW)
            );
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(
                    Component.text("Joueur introuvable.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        if (sender instanceof Player player && target.equals(player)) {
            sender.sendMessage(
                    Component.text("Vous ne pouvez pas vous mute.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        long duration;

        try {
            duration = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(
                    Component.text("Durée invalide.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        String reason = String.join(" ",
                Arrays.copyOfRange(args, 2, args.length));

        PunishmentManager punishmentManager =
                Main.getInstance().getPunishmentManager();

        if (punishmentManager.isMuted(target.getUniqueId())) {
            sender.sendMessage(
                    Component.text("Ce joueur est déjà mute.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        Player mod = sender instanceof Player p ? p : target;

        punishmentManager.mute(
                mod,
                target,
                Duration.ofMinutes(duration),
                reason
        );

        sender.sendMessage(
                Component.text(target.getName()
                                + " a été réduit au silence pour "
                                + duration + " minutes.")
                        .color(NamedTextColor.GREEN)
        );

        return true;
    }
}