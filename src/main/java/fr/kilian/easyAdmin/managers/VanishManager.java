package fr.kilian.easyAdmin.managers;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VanishManager {

    public Set<UUID> vanishedPlayers = new HashSet<>();

    public void enable(Player player){};

    public void disable(Player player){};

    public void toggle(Player player){};

    public boolean isVanished(Player player){return false;};

}
