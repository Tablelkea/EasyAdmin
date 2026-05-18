package fr.kilian.easyAdmin.listeners;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.models.enums.ModItem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NonNull;

import static fr.kilian.easyAdmin.utils.MessagesFormats.CANNOT_SELF_TARGET;

public class ModItemListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(@NonNull PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        Player player = event.getPlayer();
        if (!Main.getInstance().getModManager().isInMod(player)) return;

        String modItemType = getModItemType(event.getItem());
        if (modItemType == null) return;

        if(ModItem.LOOKUP.name().equals(modItemType)){
            Main.getInstance().getInventoryManager().allPlayersMenu(player);
        }

        else if(ModItem.TELEPORT.name().equals(modItemType)){
            Main.getInstance().getInventoryManager().allPlayersTeleportMenu(player);
        }

        else if(ModItem.VANISH.name().equals(modItemType)){
            Main.getInstance().getVanishManager().toggle(player);
        }
        else{
            event.setCancelled(true);
            return;
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteractOnEntity(@NonNull PlayerInteractEntityEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        Player player = event.getPlayer();
        if (!Main.getInstance().getModManager().isInMod(player)) return;

        String modItemType = getModItemType(player.getInventory().getItemInMainHand());
        if (modItemType == null) return;

        Entity entity = event.getRightClicked();
        if (!(entity instanceof Player target)) return;

        event.setCancelled(true);

        if (target.equals(player)) {
            player.sendMessage(Component.text(CANNOT_SELF_TARGET.getMessage()));
            return;
        }

        switch (ModItem.valueOf(modItemType)) {
            case FREEZE -> Main.getInstance().getFreezeManager().toggleFreeze(player, target);
            case INSPECT -> player.openInventory(target.getInventory());
            case LOOKUP_CLICKED_PLAYER -> Main.getInstance().getInventoryManager().createLookupMenu(player, target);
            case ENDERCHEST -> player.openInventory(target.getEnderChest());
        }
    }
    private String getModItemType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        return item.getItemMeta()
                .getPersistentDataContainer()
                .get(Main.getInstance().getModManager().getModItemKey(), PersistentDataType.STRING);
    }
}