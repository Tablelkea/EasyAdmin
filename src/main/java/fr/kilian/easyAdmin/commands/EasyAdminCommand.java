package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import static fr.kilian.easyAdmin.utils.MessagesFormats.DONT_HAVE_PERMISSION;

public class EasyAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {

        if(sender instanceof Player player){
            if (!player.hasPermission("easyadmin.ban")) {
                player.sendMessage(
                        Component.text(DONT_HAVE_PERMISSION.getMessage())
                );
            }else{
                Main.getInstance().getInventoryManager().createMainMenu(player);
            }
        }



        return false;
    }
}
