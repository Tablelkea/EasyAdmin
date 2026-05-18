package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class EasyAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {

        if(sender instanceof Player player){

            Main.getInstance().getInventoryManager().createMainMenu(player);

        }

        return false;
    }
}
