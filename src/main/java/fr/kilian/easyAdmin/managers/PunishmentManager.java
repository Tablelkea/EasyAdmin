package fr.kilian.easyAdmin.managers;

import fr.kilian.easyAdmin.models.Warning;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class PunishmentManager {

    public void ban(Player player, String reason){};

    public void tempBan(Player player, Duration duration, String reason){};

    public void kick(Player player, String reason){};

    public void warn(Player player, String reason){};

    public void mute(Player player, Duration duration){};

    public void mute(Player player, Duration duration, String reason){};

    public void unban(String playerName){};

    public List<Warning> getWarnings(UUID uuid){return List.of();};

    public boolean isBanned(UUID uuid){return false;};

    public boolean isMuted(UUID uuid){return false;};
}
