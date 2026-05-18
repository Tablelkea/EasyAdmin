package fr.kilian.easyAdmin.managers;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.models.PlayerProfile;
import fr.kilian.easyAdmin.models.Punishment;
import fr.kilian.easyAdmin.models.StaffLog;
import fr.kilian.easyAdmin.models.enums.PunishmentType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class PunishmentManager {

    private final Map<UUID, Instant> bannedPlayers = new HashMap<>();
    private final List<UUID> permanentBannedPlayers = new ArrayList<>();
    private final Map<UUID, Instant> mutedPlayers = new HashMap<>();

    public void ban(@NonNull Player mod, @NonNull Player target, String reason) {
        permanentBannedPlayers.add(target.getUniqueId());

        new StaffLog(mod.getUniqueId(), target.getUniqueId(), "BAN PERM", reason);
        Main.getInstance().getLogManager().logPunishment(mod, target, "BAN PERM");

        target.kick(
                Component.text("[ADMINISTRATION] Vous avez été banni définitivement de ce serveur.")
                        .color(NamedTextColor.RED)
        );

        PlayerProfile.getPlayerProfile(target).getPunishments().add(
                new Punishment(target.getUniqueId(), mod.getUniqueId(),
                        PunishmentType.BAN, reason, 0)
        );
    }

    public void tempBan(@NonNull Player mod,
                        @NonNull Player target,
                        Duration duration,
                        String reason) {

        bannedPlayers.put(
                target.getUniqueId(),
                Instant.now().plus(duration)
        );

        new StaffLog(mod.getUniqueId(), target.getUniqueId(), "TEMPBAN", reason);
        Main.getInstance().getLogManager().logPunishment(mod, target, "TEMPBAN");

        target.kick(
                Component.text("[ADMINISTRATION] Vous avez été banni pour "
                                + duration.toMinutes() + " minutes.")
                        .color(NamedTextColor.RED)
        );

        PlayerProfile.getPlayerProfile(target).getPunishments().add(
                new Punishment(target.getUniqueId(), mod.getUniqueId(),
                        PunishmentType.TEMPBAN, reason, duration.toMillis())
        );
    }

    public void kick(@NonNull Player mod, @NonNull Player target, String reason) {
        PlayerProfile.getPlayerProfile(target).getPunishments().add(
                new Punishment(target.getUniqueId(), mod.getUniqueId(),
                        PunishmentType.KICK, reason, 0)
        );

        Main.getInstance().getLogManager().logPunishment(mod, target, "KICK");
        new StaffLog(mod.getUniqueId(), target.getUniqueId(), "KICK", reason);

        target.kick(
                Component.text("[ADMINISTRATION] Vous avez été expulsé : " + reason)
                        .color(NamedTextColor.RED)
        );
    }

    public void warn(@NonNull Player mod, @NonNull Player target, String reason) {
        PlayerProfile.getPlayerProfile(target).getPunishments().add(
                new Punishment(target.getUniqueId(), mod.getUniqueId(),
                        PunishmentType.WARN, reason, 0)
        );

        Main.getInstance().getLogManager().logPunishment(mod, target, "WARN");
        new StaffLog(mod.getUniqueId(), target.getUniqueId(), "WARN", reason);

        target.sendMessage(
                Component.text("[ADMINISTRATION] Avertissement par "
                        + mod.getName() + " : " + reason)
        );
    }

    public void mute(@NonNull Player mod,
                     @NonNull Player target,
                     Duration duration,
                     String reason) {

        mutedPlayers.put(
                target.getUniqueId(),
                Instant.now().plus(duration)
        );

        PlayerProfile.getPlayerProfile(target).getPunishments().add(
                new Punishment(target.getUniqueId(), mod.getUniqueId(),
                        PunishmentType.MUTE, reason, duration.toMillis())
        );

        Main.getInstance().getLogManager().logPunishment(mod, target, "MUTE");
        new StaffLog(mod.getUniqueId(), target.getUniqueId(), "MUTE", reason);

        target.sendMessage(
                Component.text("[ADMINISTRATION] Vous êtes mute pour "
                                + duration.toMinutes() + " minutes.")
                        .color(NamedTextColor.RED)
        );
    }

    public void unmute(@NonNull Player mod, @NonNull Player target) {
        if (!isMuted(target.getUniqueId()))
            return;

        mutedPlayers.remove(target.getUniqueId());

        Main.getInstance().getLogManager().logPunishment(mod, target, "UNMUTE");
        new StaffLog(mod.getUniqueId(), target.getUniqueId(), "UNMUTE", "");

        target.sendMessage(
                Component.text("[ADMINISTRATION] Vous n'êtes plus réduit au silence.")
                        .color(NamedTextColor.GREEN)
        );
    }

    public void unmute(@NonNull Player target) {
        if (!isMuted(target.getUniqueId()))
            return;

        mutedPlayers.remove(target.getUniqueId());

        target.sendMessage(
                Component.text("[ADMINISTRATION] Vous n'êtes plus réduit au silence.")
                        .color(NamedTextColor.GREEN)
        );
    }

    public void unban(@NonNull Player mod, @NonNull UUID playerUUID) {
        bannedPlayers.remove(playerUUID);
        permanentBannedPlayers.remove(playerUUID);

        Main.getInstance().getLogManager()
                .log("[SANCTION] MODERATEUR: " + mod.getName()
                        + " | JOUEUR: " + playerUUID
                        + " | ACTION: DEBAN");

        new StaffLog(mod.getUniqueId(), playerUUID, "DEBAN", "");
    }

    public boolean isBanned(@NonNull UUID uuid) {
        if (permanentBannedPlayers.contains(uuid)) {
            return true;
        }

        Instant expire = bannedPlayers.get(uuid);

        if (expire == null) {
            return false;
        }

        if (Instant.now().isAfter(expire)) {
            bannedPlayers.remove(uuid);
            return false;
        }

        return true;
    }

    public boolean isMuted(@NonNull UUID uuid) {
        Instant expire = mutedPlayers.get(uuid);

        if (expire == null) {
            return false;
        }

        if (Instant.now().isAfter(expire)) {
            mutedPlayers.remove(uuid);
            return false;
        }

        return true;
    }
}