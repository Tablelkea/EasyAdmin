package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.PunishmentManager;
import fr.kilian.easyAdmin.models.PlayerProfile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UnbanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof Player player &&
                !player.hasPermission("easyadmin.unban")) {
            sender.sendMessage(
                    Component.text("Permission insuffisante.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(
                    Component.text("Usage: /unban <joueur>")
                            .color(NamedTextColor.YELLOW)
            );
            return true;
        }

        String targetName = args[0];

        UUID targetUUID = PlayerProfile
                .get(targetName);

        if (targetUUID == null) {
            sender.sendMessage(
                    Component.text("Joueur inconnu.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        PunishmentManager punishmentManager =
                Main.getInstance().getPunishmentManager();

        if (!punishmentManager.isBanned(targetUUID)) {
            sender.sendMessage(
                    Component.text("Ce joueur n'est pas banni.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        Player mod = sender instanceof Player p
                ? p
                : Bukkit.getOnlinePlayers().stream().findFirst().orElse(null);

        if (mod == null) {
            sender.sendMessage(
                    Component.text("Aucun joueur connecté pour exécuter cette commande.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        punishmentManager.unban(mod, targetUUID);

        sender.sendMessage(
                Component.text(targetName + " a été débanni.")
                        .color(NamedTextColor.GREEN)
        );

        return true;
    }
}