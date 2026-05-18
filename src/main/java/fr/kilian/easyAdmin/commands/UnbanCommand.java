package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.PunishmentManager;
import fr.kilian.easyAdmin.models.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static fr.kilian.easyAdmin.utils.MessagesFormats.*;

public class UnbanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof Player player &&
                !player.hasPermission("easyadmin.unban")) {
            sender.sendMessage(
                    Component.text(DONT_HAVE_PERMISSION.getMessage())
            );
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(
                    Component.text(UNBAN_HELP.getMessage())
            );
            return true;
        }

        String targetName = args[0];

        UUID targetUUID = PlayerProfile
                .get(targetName);

        if (targetUUID == null) {
            sender.sendMessage(
                    Component.text(PLAYER_NOT_FOUND.getMessage())
            );
            return true;
        }

        PunishmentManager punishmentManager =
                Main.getInstance().getPunishmentManager();

        if (!punishmentManager.isBanned(targetUUID)) {
            sender.sendMessage(
                    Component.text(PLAYER_NOT_BANNED.getMessage())
            );
            return true;
        }

        Player mod = sender instanceof Player p
                ? p
                : Bukkit.getOnlinePlayers().stream().findFirst().orElse(null);

        if (mod == null) {
            sender.sendMessage(
                    Component.text(NO_ONLINE_PLAYER.getMessage())
            );
            return true;
        }

        punishmentManager.unban(mod, targetUUID);

        return true;
    }
}