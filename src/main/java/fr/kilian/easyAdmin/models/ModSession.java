package fr.kilian.easyAdmin.models;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.UUID;

public class ModSession {

    private final UUID player;

    private final ItemStack[] savedInventory;
    private final ItemStack[] savedArmor;

    private final Location savedLocation;

    private boolean vanished;
    private boolean flying;

    private final long startedAt;

    public ModSession(UUID player, ItemStack[] savedInventory, ItemStack[] savedArmor, Location savedLocation, boolean vanished, boolean flying) {
        this.player = player;
        this.savedInventory = savedInventory;
        this.savedArmor = savedArmor;
        this.savedLocation = savedLocation;
        this.vanished = vanished;
        this.flying = flying;
        this.startedAt = new Date().getTime();
    }

    public UUID getPlayer() {
        return player;
    }

    public ItemStack[] getSavedInventory() {
        return savedInventory;
    }

    public ItemStack[] getSavedArmor() {
        return savedArmor;
    }

}
