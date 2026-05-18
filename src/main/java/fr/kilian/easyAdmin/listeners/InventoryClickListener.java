package fr.kilian.easyAdmin.listeners;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.InventoryManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.time.Duration;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(@NonNull InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) return;

        InventoryManager inventoryManager = Main.getInstance().getInventoryManager();

        if (Main.getInstance().getFreezeManager().isFrozen(player)) {
            event.setCancelled(true);
            return;
        }

        if (event.getCurrentItem() == null || event.getCurrentItem().getType().isAir()) {
            return;
        }

        String title = PlainTextComponentSerializer.plainText()
                .serialize(event.getView().title());

        ItemStack clicked = event.getCurrentItem();

        if (title.equals("Menu Principal")) {
            event.setCancelled(true);

            if (clicked.isSimilar(inventoryManager.playerList())) {
                inventoryManager.allPlayersMenu(player);

            } else if (clicked.isSimilar(inventoryManager.modButton())) {
                Main.getInstance().getModManager().toggleMod(player);

            } else if (clicked.isSimilar(inventoryManager.vanishButton())) {
                Main.getInstance().getVanishManager().toggle(player);
            }
        }

        else if (title.equals("Joueurs en ligne")) {
            event.setCancelled(true);

            if (clicked.getItemMeta() == null) return;

            Player target = Bukkit.getPlayer(
                    PlainTextComponentSerializer.plainText()
                            .serialize(clicked.getItemMeta().displayName())
            );

            if (target != null) {
                inventoryManager.createLookupMenu(player, target);
            }
        }

        else if (title.startsWith("Inspection: ")) {
            event.setCancelled(true);

            Player target = Bukkit.getPlayer(title.replace("Inspection: ", ""));
            if (target == null) return;

            if (clicked.isSimilar(inventoryManager.teleportPlayer())) {
                player.teleport(target.getLocation());

            } else if (clicked.isSimilar(inventoryManager.invseePlayer())) {
                player.openInventory(target.getInventory());

            } else if (clicked.isSimilar(inventoryManager.invseeEnderchestPlayer())) {
                player.openInventory(target.getEnderChest());

            } else if (clicked.isSimilar(inventoryManager.mutePlayer())) {
                Main.getInstance().getPunishmentManager()
                        .mute(player, target, Duration.ofMinutes(10), "LOOKUP REASON");

            } else if (clicked.isSimilar(inventoryManager.banPermPlayer())) {
                Main.getInstance().getPunishmentManager()
                        .ban(player, target, "LOOKUP REASON");

            } else if (clicked.isSimilar(inventoryManager.lookupPlayer())) {

                var lm = Main.getInstance().getLookupManager();

                player.sendMessage(Component.empty());
                player.sendMessage(Component.text("══════ Profil de ", NamedTextColor.GOLD)
                        .append(Component.text(target.getName(), NamedTextColor.WHITE))
                        .append(Component.text(" ══════", NamedTextColor.GOLD)));
                player.sendMessage(Component.text("Première connexion : ", NamedTextColor.GRAY)
                        .append(Component.text(lm.getFirstLogin(target), NamedTextColor.WHITE)));
                player.sendMessage(Component.text("Dernière connexion : ", NamedTextColor.GRAY)
                        .append(Component.text(lm.getLastLogin(target), NamedTextColor.WHITE)));
                player.sendMessage(Component.text("Sanctions : ", NamedTextColor.GRAY)
                        .append(Component.text(lm.getHistory(target).size() + "", NamedTextColor.RED)));

                var punishments = lm.getHistory(target);
                if (!punishments.isEmpty()) {
                    player.sendMessage(Component.text("Historique :", NamedTextColor.GRAY));
                    for (var p : punishments) {
                        player.sendMessage(Component.text("  • ", NamedTextColor.DARK_GRAY)
                                .append(Component.text(p.toString(), NamedTextColor.WHITE)));
                    }
                }

                player.sendMessage(Component.text("═══════════════════════════════", NamedTextColor.GOLD));
                player.sendMessage(Component.empty());

            } else if (clicked.isSimilar(inventoryManager.freezeButton())) {
                Main.getInstance().getFreezeManager()
                        .toggleFreeze(player, target);
            }
        }
    }
}