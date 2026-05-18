package fr.kilian.easyAdmin;

import fr.kilian.easyAdmin.commands.*;
import fr.kilian.easyAdmin.listeners.*;
import fr.kilian.easyAdmin.managers.*;
import fr.kilian.easyAdmin.managers.gui.ModMenuGUI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

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
        instance = this;

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
        pm.registerEvents(new ChatListener(), this);

        getCommand("easyadmin").setExecutor(new EasyAdminCommand());
        getCommand("lookup").setExecutor(new LookupCommand());
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
        getCommand("freeze").setExecutor(new FreezeCommand());
        getCommand("unfreeze").setExecutor(new UnfreezeCommand());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new UnmuteCommand());
        getCommand("tempban").setExecutor(new TempbanCommand());
        getCommand("warn").setExecutor(new WarnCommand());

        System.out.println("PLUGIN START");

    }

    @Override
    public void onDisable() {
    }

    public static Main getInstance() {
        return instance;
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
