package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PermissionCommand implements CommandExecutor, TabCompleter {

    private final PermissionManager permissionManager =
            Main.getInstance().getPermissionManager();

    @Override
    public boolean onCommand(@NonNull CommandSender sender,
                             @NonNull Command command,
                             @NonNull String label,
                             @NonNull String[] args) {

        if (!(sender instanceof Player mod)) {
            sender.sendMessage("Commande réservée aux joueurs.");
            return true;
        }

        if (!mod.hasPermission("easyadmin.permission")) {
            mod.sendMessage("§cPermission insuffisante.");
            return true;
        }

        if (args.length < 2) {
            sendHelp(mod);
            return true;
        }

        String action = args[0].toLowerCase();
        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            mod.sendMessage("§cJoueur introuvable.");
            return true;
        }

        switch (action) {

            case "give" -> {
                if (args.length < 3) {
                    sendHelp(mod);
                    return true;
                }

                Permission permission =
                        new Permission(args[2]);

                permissionManager.givePermission(target, permission);

                mod.sendMessage("§aPermission donnée.");
            }

            case "temp" -> {
                if (args.length < 4) {
                    sendHelp(mod);
                    return true;
                }

                Permission permission =
                        new Permission(args[2]);

                long minutes;

                try {
                    minutes = Long.parseLong(args[3]);
                } catch (NumberFormatException e) {
                    mod.sendMessage("§cTemps invalide.");
                    return true;
                }

                permissionManager.giveTemporaryPermission(
                        target,
                        permission,
                        minutes * 60 * 1000
                );

                mod.sendMessage("§aPermission temporaire donnée.");
            }

            case "remove" -> {
                if (args.length < 3) {
                    sendHelp(mod);
                    return true;
                }

                Permission permission =
                        new Permission(args[2]);

                permissionManager.removePermission(target, permission);

                mod.sendMessage("§aPermission retirée.");
            }

            case "list" -> {
                List<String> permissions =
                        permissionManager.getPermissions(target);

                mod.sendMessage("§6Permissions de " + target.getName() + ":");

                if (permissions.isEmpty()) {
                    mod.sendMessage("§7Aucune permission.");
                } else {
                    for (String permission : permissions) {
                        mod.sendMessage("§7- " + permission);
                    }
                }
            }

            default -> sendHelp(mod);
        }

        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage("§6/perm give <joueur> <permission>");
        player.sendMessage("§6/perm temp <joueur> <permission> <minutes>");
        player.sendMessage("§6/perm remove <joueur> <permission>");
        player.sendMessage("§6/perm list <joueur>");
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender,
                                      @NonNull Command command,
                                      @NonNull String alias,
                                      @NonNull String[] args) {

        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("give");
            completions.add("temp");
            completions.add("remove");
            completions.add("list");
        }

        if (args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        }

        return completions;
    }
}