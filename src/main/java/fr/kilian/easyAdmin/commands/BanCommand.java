package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.PunishmentManager;
import static fr.kilian.easyAdmin.utils.MessagesFormats.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof Player player &&
                !player.hasPermission("easyadmin.ban")) {
            player.sendMessage(
                    Component.text(DONT_HAVE_PERMISSION.getMessage())
            );
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(BAN_HELP.getMessage());
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(PLAYER_NOT_FOUND.getMessage());
            return true;
        }

        if (sender instanceof Player player && target.equals(player)) {
            sender.sendMessage(CANNOT_SELF_BAN.getMessage());
            return true;
        }

        String reason = String.join(" ",
                java.util.Arrays.copyOfRange(args, 1, args.length));

        PunishmentManager punishmentManager =
                Main.getInstance().getPunishmentManager();

        if (punishmentManager.isBanned(target.getUniqueId())) {
            sender.sendMessage(PLAYER_ALREADY_BAN.getMessage());
            return true;
        }

        Player mod = sender instanceof Player p ? p : target;

        punishmentManager.ban(mod, target, reason);
        return true;
    }
}