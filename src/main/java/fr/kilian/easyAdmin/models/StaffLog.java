package fr.kilian.easyAdmin.models;

import java.util.UUID;

public class StaffLog {

    private UUID moderator;
    private UUID target;

    private String action;

    private String details;

    private long timestamp;

}
