package fr.kilian.easyAdmin.models;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ModSession {

    private UUID player;

    private ItemStack[] savedInventory;
    private ItemStack[] savedArmor;

    private Location savedLocation;

    private boolean vanished;
    private boolean flying;

    private long startedAt;



}
