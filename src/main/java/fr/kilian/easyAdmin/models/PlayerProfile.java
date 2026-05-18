package fr.kilian.easyAdmin.models;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class PlayerProfile {

    public static final Map<UUID, PlayerProfile> playerProfiles = new HashMap<>();
    public static Map<String, UUID> playersUUID = new HashMap<>();

    private final UUID uuid;

    private String name;
    private final Date firstJoin;
    private Date lastLogin;
    private long playTime;

    private final List<Punishment> punishments;

    public PlayerProfile(UUID uuid, String name, Date firstJoin) {
        this.uuid = uuid;
        this.name = name;

        this.firstJoin = firstJoin;
        this.lastLogin = firstJoin;

        this.punishments = new ArrayList<>();

        playerProfiles.put(uuid, this);
        playersUUID.put(name, uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFirstJoin() {
        return firstJoin;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date date) {
        this.lastLogin = date;
    }

    public long getPlayTime() {
        updatePlayTime();
        return playTime;
    }

    public void updatePlayTime() {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            this.playTime = player.getStatistic(Statistic.TOTAL_WORLD_TIME);
        }
    }

    public List<Punishment> getPunishments() {
        return punishments;
    }

    public void addPunishment(Punishment punishment) {
        this.punishments.add(punishment);
    }

    public static PlayerProfile getPlayerProfile(@NonNull Player player) {
        return playerProfiles.get(player.getUniqueId());
    }

    public static PlayerProfile get(UUID uuid) {
        return playerProfiles.get(uuid);
    }

    public static UUID get(String playerName) {
        return playersUUID.get(playerName);
    }
}