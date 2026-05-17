package fr.kilian.easyAdmin.models;

import java.util.UUID;

public class CustomPermission {

    private UUID player;

    private String permission;

    private long expiresAt;

    public boolean isExpired(){return false;};

}
