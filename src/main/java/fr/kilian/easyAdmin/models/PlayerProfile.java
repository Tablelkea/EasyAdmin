package fr.kilian.easyAdmin.models;

import java.util.List;
import java.util.UUID;

public class PlayerProfile {

    private UUID uuid;
    private String name;

    private long firstJoin;
    private long lastJoin;
    private long playTime;

    private double balance;
    private String rank;

    private List<Punishment> punishments;

    public void addPlayTime(long time){};

    public void addPunishment(Punishment punishment){};

}
