package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.FreezeManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FreezeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof Player player &&
                !player.hasPermission("easyadmin.freeze")) {
            sender.sendMessage("Permission insuffisante.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("Usage: /freeze <joueur> [raison]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage("Joueur introuvable.");
            return true;
        }

        if (sender instanceof Player player && target.equals(player)) {
            sender.sendMessage("Vous ne pouvez pas vous freeze.");
            return true;
        }

        String reason = args.length > 1
                ? String.join(" ",
                java.util.Arrays.copyOfRange(args, 1, args.length))
                : "Aucune raison";

        FreezeManager freezeManager =
                Main.getInstance().getFreezeManager();

        if (freezeManager.isFrozen(target)) {
            sender.sendMessage("Ce joueur est déjà freeze.");
            return true;
        }

        Player mod = sender instanceof Player p ? p : target;

        freezeManager.freeze(mod, target, reason);

        sender.sendMessage(target.getName() + " a été freeze.");
        return true;
    }
}