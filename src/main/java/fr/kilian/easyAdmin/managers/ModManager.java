package fr.kilian.easyAdmin.managers;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.models.enums.ModItem;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static fr.kilian.easyAdmin.utils.MessagesFormats.LEAVE_MODERATOR_MODE;
import static fr.kilian.easyAdmin.utils.MessagesFormats.SWITCH_TO_MODERATOR_MODE;

public class ModManager {

    private final NamespacedKey modItemKey;

    private final Map<UUID, fr.kilian.easyAdmin.models.ModSession> sessions = new HashMap<>();

    public ModManager() {
        this.modItemKey = new NamespacedKey(Main.getInstance(), ModItem.PDC_KEY);
    }


    public void enableMod(@NonNull Player player) {
        saveInventory(player);
        giveModItems(player);
        player.sendMessage(Component.text(SWITCH_TO_MODERATOR_MODE.getMessage()));
        Main.getInstance().getLogManager().logModToggle(player, true);
    }

    public void disableMod(@NonNull Player player) {
        clearModItems(player);
        restoreInventory(player);
        player.sendMessage(Component.text(LEAVE_MODERATOR_MODE.getMessage()));
        Main.getInstance().getLogManager().logModToggle(player, false);
    }

    public void toggleMod(@NonNull Player player) {
        if (isInMod(player))
            disableMod(player);
        else
            enableMod(player);
    }

    public boolean isInMod(@NonNull Player player) {
        return sessions.containsKey(player.getUniqueId());
    }


    public void saveInventory(@NonNull Player player) {
        ItemStack[] savedInventory = player.getInventory().getContents().clone();
        ItemStack[] savedArmor     = player.getInventory().getArmorContents().clone();

        fr.kilian.easyAdmin.models.ModSession session = new fr.kilian.easyAdmin.models.ModSession(
                player.getUniqueId(),
                savedInventory,
                savedArmor,
                player.getLocation(),
                Main.getInstance().getVanishManager().isVanished(player),
                player.isFlying()
        );
        sessions.put(player.getUniqueId(), session);
        player.getInventory().clear();
    }

    public void restoreInventory(@NonNull Player player) {
        fr.kilian.easyAdmin.models.ModSession session = sessions.get(player.getUniqueId());
        if (session == null) return;

        player.getInventory().clear();
        player.getInventory().setArmorContents(session.getSavedArmor());
        player.getInventory().setContents(session.getSavedInventory());
        sessions.remove(player.getUniqueId());
    }

    public void giveModItems(@NonNull Player player) {
        for (ModItem modItem : ModItem.values()) {
            player.getInventory().setItem(modItem.getSlot(), modItem.build(modItemKey));
        }
    }

    public void clearModItems(@NonNull Player player) {
        var inv = player.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack == null) continue;
            if (!stack.hasItemMeta()) continue;
            if (stack.getItemMeta().getPersistentDataContainer()
                    .has(modItemKey, org.bukkit.persistence.PersistentDataType.STRING)) {
                inv.setItem(i, null);
            }
        }
    }

    public NamespacedKey getModItemKey() {
        return modItemKey;
    }

    public fr.kilian.easyAdmin.models.ModSession getSession(@NonNull Player player) {
        return sessions.get(player.getUniqueId());
    }
}