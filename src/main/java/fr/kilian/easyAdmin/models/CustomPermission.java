package fr.kilian.easyAdmin.models;

import java.util.Date;
import java.util.UUID;

public class CustomPermission {

    private final UUID player;

    private final String permission;

    private final long expiresAt;

    public CustomPermission(UUID player, String permission, long expiresAt) {
        this.player = player;
        this.permission = permission;
        this.expiresAt = expiresAt;
    }

    public UUID getPlayer() {
        return player;
    }

    public String getPermission() {
        return permission;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public boolean isExpired(){return (new Date().getTime() >= expiresAt);};

}
