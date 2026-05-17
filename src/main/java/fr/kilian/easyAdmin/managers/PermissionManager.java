package fr.kilian.easyAdmin.managers;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.List;

public class PermissionManager {

    public void givePermission(Player player, Permission permission){};

    public void removePermission(Player player, Permission permission){};

    public boolean hasPermission(Player player, Permission permission){return false;};

    public List<String> getPermissions(Player player){return List.of();};

}
