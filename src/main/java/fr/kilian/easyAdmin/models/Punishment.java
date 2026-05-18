package fr.kilian.easyAdmin.models;

import fr.kilian.easyAdmin.models.enums.PunishmentType;

import java.util.Date;
import java.util.UUID;

public class Punishment {

    private final UUID target;
    private final UUID moderator;

    private final PunishmentType type;

    private final String reason;

    private final long createdAt;
    private long expiresAt;

    private boolean active;

    public Punishment(UUID target, UUID moderator, PunishmentType type, String reason, long expiresAt) {
        this.target = target;
        this.moderator = moderator;
        this.type = type;
        this.reason = reason;
        this.createdAt = new Date().getTime();
        this.expiresAt = new Date().getTime()+expiresAt;
        this.active = true;
    }

    public UUID getTarget() {
        return target;
    }

    public UUID getModerator() {
        return moderator;
    }

    public PunishmentType getType() {
        return type;
    }

    public String getReason() {
        return reason;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    boolean isExpired(){return false;};

}
