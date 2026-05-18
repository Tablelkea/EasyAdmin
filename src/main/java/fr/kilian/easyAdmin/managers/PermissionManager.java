package fr.kilian.easyAdmin.managers;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.models.CustomPermission;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class PermissionManager {

    private final Map<UUID, List<CustomPermission>> permissions = new HashMap<>();
    private final Map<UUID, PermissionAttachment> attachments = new HashMap<>();

    private PermissionAttachment getAttachment(Player player) {
        return attachments.computeIfAbsent(
                player.getUniqueId(),
                uuid -> player.addAttachment(Main.getInstance())
        );
    }

    public void givePermission(@NonNull Player player,
                               @NonNull Permission permission) {

        CustomPermission customPermission = new CustomPermission(
                player.getUniqueId(),
                permission.getName(),
                Long.MAX_VALUE
        );

        permissions
                .computeIfAbsent(player.getUniqueId(), k -> new ArrayList<>())
                .add(customPermission);

        getAttachment(player).setPermission(permission.getName(), true);

        player.sendMessage("§aPermission ajoutée : " + permission.getName());
    }

    public void removePermission(@NonNull Player player,
                                 @NonNull Permission permission) {

        List<CustomPermission> playerPerms =
                permissions.get(player.getUniqueId());

        if (playerPerms != null) {
            playerPerms.removeIf(p ->
                    p.getPermission().equals(permission.getName()));
        }

        PermissionAttachment attachment =
                attachments.get(player.getUniqueId());

        if (attachment != null) {
            attachment.unsetPermission(permission.getName());
        }

        player.sendMessage("§cPermission retirée : " + permission.getName());
    }

    public boolean hasPermission(@NonNull Player player,
                                 @NonNull Permission permission) {

        cleanupExpired(player);

        return player.hasPermission(permission.getName());
    }

    public List<String> getPermissions(@NonNull Player player) {

        cleanupExpired(player);

        List<CustomPermission> playerPerms =
                permissions.getOrDefault(
                        player.getUniqueId(),
                        Collections.emptyList()
                );

        List<String> result = new ArrayList<>();

        for (CustomPermission permission : playerPerms) {
            result.add(permission.getPermission());
        }

        return result;
    }

    public void giveTemporaryPermission(@NonNull Player player,
                                        @NonNull Permission permission,
                                        long durationMillis) {

        long expiresAt = System.currentTimeMillis() + durationMillis;

        CustomPermission customPermission = new CustomPermission(
                player.getUniqueId(),
                permission.getName(),
                expiresAt
        );

        permissions
                .computeIfAbsent(player.getUniqueId(), k -> new ArrayList<>())
                .add(customPermission);

        getAttachment(player).setPermission(permission.getName(), true);

        player.sendMessage(
                "§aPermission temporaire ajoutée : "
                        + permission.getName()
        );
    }

    private void cleanupExpired(Player player) {
        List<CustomPermission> playerPerms =
                permissions.get(player.getUniqueId());

        if (playerPerms == null)
            return;

        Iterator<CustomPermission> iterator =
                playerPerms.iterator();

        while (iterator.hasNext()) {
            CustomPermission permission =
                    iterator.next();

            if (permission.isExpired()) {
                iterator.remove();

                PermissionAttachment attachment =
                        attachments.get(player.getUniqueId());

                if (attachment != null) {
                    attachment.unsetPermission(
                            permission.getPermission()
                    );
                }
            }
        }
    }
}