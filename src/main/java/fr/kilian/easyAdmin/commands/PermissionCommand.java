package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

import static fr.kilian.easyAdmin.utils.MessagesFormats.*;

public class PermissionCommand implements CommandExecutor, TabCompleter {

    private final PermissionManager permissionManager =
            Main.getInstance().getPermissionManager();

    @Override
    public boolean onCommand(@NonNull CommandSender sender,
                             @NonNull Command command,
                             @NonNull String label,
                             @NonNull String[] args) {

        if (!(sender instanceof Player mod)) {
            sender.sendMessage(AVAILABLE_ONLY_INGAME.getMessage());
            return true;
        }

        if (!mod.hasPermission("easyadmin.permission")) {
            mod.sendMessage(DONT_HAVE_PERMISSION.getMessage());
            return true;
        }

        if (args.length < 2) {
            sendHelp(mod);
            return true;
        }

        String action = args[0].toLowerCase();
        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            mod.sendMessage(PLAYER_NOT_FOUND.getMessage());
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

                mod.sendMessage(PERMISSION_GIVE.getMessage());
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
                    mod.sendMessage(BAD_TIME_ARGUMENT.getMessage());
                    return true;
                }

                permissionManager.giveTemporaryPermission(
                        target,
                        permission,
                        minutes * 60 * 1000
                );

                mod.sendMessage(TEMPORARY_PERMISSION_GIVE.getMessage());
            }

            case "remove" -> {
                if (args.length < 3) {
                    sendHelp(mod);
                    return true;
                }

                Permission permission =
                        new Permission(args[2]);

                permissionManager.removePermission(target, permission);

                mod.sendMessage(PERMISSION_REMOVE.getMessage());
            }

            case "list" -> {
                List<String> permissions =
                        permissionManager.getPermissions(target);

                mod.sendMessage("§6Permissions de " + target.getName() + ":");

                if (permissions.isEmpty()) {
                    mod.sendMessage(NO_PERMISSION.getMessage());
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
        player.sendMessage(PERMISSION_GIVE_HELP.getMessage());
        player.sendMessage(PERMISSION_TEMP_HELP.getMessage());
        player.sendMessage(PERMISSION_REMOVE_HELP.getMessage());
        player.sendMessage(PERMISSION_LIST_HELP.getMessage());
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