package fr.kilian.easyAdmin.models;

import fr.kilian.easyAdmin.models.enums.PunishmentType;

import java.util.UUID;

public class Punishment {

    private UUID target;
    private UUID moderator;

    private PunishmentType type;

    private String reason;

    private long createdAt;
    private long expiresAt;

    private boolean active;

    boolean isExpired(){return false;};

}
