package fr.kilian.easyAdmin.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class StaffLog {

    public static List<StaffLog> logs = new ArrayList<>();

    private final UUID moderator;
    private final UUID target;

    private final String action;

    private final String details;

    private final long timestamp;

    public StaffLog(UUID moderator, UUID target, String action, String details) {
        this.moderator = moderator;
        this.target = target;
        this.action = action;
        this.details = details;
        this.timestamp = new Date().getTime();

        logs.add(this);
    }

    public UUID getModerator() {
        return moderator;
    }

    public UUID getTarget() {
        return target;
    }

    public String getAction() {
        return action;
    }

    public String getDetails() {
        return details;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
