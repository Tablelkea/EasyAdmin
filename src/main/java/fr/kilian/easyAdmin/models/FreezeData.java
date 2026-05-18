package fr.kilian.easyAdmin.models;

import java.util.Date;
import java.util.UUID;

public class FreezeData {

    private final UUID target;

    private final UUID moderator;

    private final long startedAt;

    private final String reason;

    public FreezeData(UUID target, UUID moderator, String reason) {
        this.target = target;
        this.moderator = moderator;
        this.startedAt = new Date().getTime();
        this.reason = reason;
    }

    public UUID getTarget() {
        return target;
    }

}
