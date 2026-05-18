package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.FreezeManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static fr.kilian.easyAdmin.utils.MessagesFormats.*;

public class FreezeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof Player player &&
                !player.hasPermission("easyadmin.freeze")) {
            sender.sendMessage(DONT_HAVE_PERMISSION.getMessage());
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(FREEZE_HELP.getMessage());
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(PLAYER_NOT_FOUND.getMessage());
            return true;
        }

        if (sender instanceof Player player && target.equals(player)) {
            sender.sendMessage(CANNOT_SELF_FREEZE.getMessage());
            return true;
        }

        String reason = args.length > 1
                ? String.join(" ",
                java.util.Arrays.copyOfRange(args, 1, args.length))
                : NO_REASON.getMessage();

        FreezeManager freezeManager =
                Main.getInstance().getFreezeManager();

        if (freezeManager.isFrozen(target)) {
            sender.sendMessage(PLAYER_ALREADY_FREEZE.getMessage());
            return true;
        }

        Player mod = sender instanceof Player p ? p : target;

        freezeManager.freeze(mod, target, reason);
        return true;
    }
}