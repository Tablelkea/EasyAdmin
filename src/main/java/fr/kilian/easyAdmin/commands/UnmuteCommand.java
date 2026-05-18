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

public class UnmuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof Player player &&
                !player.hasPermission("easyadmin.unmute")) {
            sender.sendMessage(
                    Component.text("Permission insuffisante")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(
                    Component.text("Usage: /unmute <joueur>")
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

        PunishmentManager punishmentManager =
                Main.getInstance().getPunishmentManager();

        if (!punishmentManager.isMuted(target.getUniqueId())) {
            sender.sendMessage(
                    Component.text("Ce joueur n'est pas mute.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        if (sender instanceof Player player) {
            punishmentManager.unmute(player, target);
        } else {
            punishmentManager.unmute(target);
        }

        sender.sendMessage(
                Component.text(target.getName() + " a été unmute.")
                        .color(NamedTextColor.GREEN)
        );

        return true;
    }
}