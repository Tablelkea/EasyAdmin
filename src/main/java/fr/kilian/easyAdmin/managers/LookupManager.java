package fr.kilian.easyAdmin.managers;

import fr.kilian.easyAdmin.models.PlayerProfile;
import fr.kilian.easyAdmin.models.Punishment;
import org.bukkit.entity.Player;

import java.util.List;

public class LookupManager {

    public PlayerProfile getProfile(Player player){return null;};

    public long getPlayTime(Player player){return null;};

    public List<Punishment> getHistory(Player player){return List.of();};

    public String getLastLogin(Player player){return null;};

    public String getFirstLogin(Player player){return null;};

}
