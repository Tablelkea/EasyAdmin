package fr.kilian.easyAdmin;

import fr.kilian.easyAdmin.listeners.*;
import fr.kilian.easyAdmin.managers.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private FreezeManager freezeManager;
    private InventoryManager inventoryManager;
    private LogManager logManager;
    private LookupManager lookupManager;
    private ModManager modManager;
    private PermissionManager permissionManager;
    private PunishmentManager punishmentManager;
    private VanishManager vanishManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        freezeManager = new FreezeManager();
        inventoryManager = new InventoryManager();
        logManager = new LogManager();
        lookupManager = new LookupManager();
        modManager = new ModManager();
        permissionManager = new PermissionManager();
        punishmentManager = new PunishmentManager();
        vanishManager = new VanishManager();

        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new ModItemListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new FreezeListener(), this);
        pm.registerEvents(new VanishListener(), this);
        pm.registerEvents(new PunishmentListener(), this);
        pm.registerEvents(new ProtectionListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FreezeManager getFreezeManager() {
        return freezeManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public LogManager getLogManager() {
        return logManager;
    }

    public LookupManager getLookupManager() {
        return lookupManager;
    }

    public ModManager getModManager() {
        return modManager;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    public VanishManager getVanishManager() {
        return vanishManager;
    }
}
