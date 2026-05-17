package fr.kilian.easyAdmin.managers;

import org.bukkit.entity.Player;

public class LogManager {

    public void log(String message){};

    public void logPunishment(Player mod, Player target, String action){};

    public void logModToggle(Player player, boolean enabled){};

    public void sendDiscord(String message){};

}
