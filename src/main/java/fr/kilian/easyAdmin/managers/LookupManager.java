package fr.kilian.easyAdmin.managers;

import fr.kilian.easyAdmin.models.PlayerProfile;
import fr.kilian.easyAdmin.models.Punishment;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class LookupManager {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm").withZone(ZoneId.systemDefault());

    public long getPlayTime(@NonNull Player player) {
        return PlayerProfile.getPlayerProfile(player).getPlayTime();
    }

    public List<Punishment> getHistory(@NonNull Player player) {
        return PlayerProfile.getPlayerProfile(player).getPunishments();
    }

    public String getLastLogin(@NonNull Player player) {
        Date lastJoin = PlayerProfile.getPlayerProfile(player).getLastLogin();
        return formatDate(lastJoin);
    }

    public String getFirstLogin(@NonNull Player player) {
        Date firstJoin = PlayerProfile.getPlayerProfile(player).getFirstJoin();
        return formatDate(firstJoin);
    }


    private String formatDate(Date date) {
        return DATE_FORMATTER.format(Instant.ofEpochMilli(date.getTime()));
    }

}