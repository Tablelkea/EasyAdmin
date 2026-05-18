package fr.kilian.easyAdmin.managers;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import fr.kilian.easyAdmin.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class InventoryManager {

    public ItemStack playerList() {
        return ItemBuilder.build(
                Material.PLAYER_HEAD,
                "§b§lJoueurs en ligne",
                List.of(
                        Component.text("§7Clique pour voir la liste"),
                        Component.text("§8des joueurs connectés")
                )
        );
    }

    public ItemStack modButton() {
        return ItemBuilder.build(
                Material.BLAZE_ROD,
                "§c§lMode Modérateur",
                List.of(
                        Component.text("§7Clique pour activer le mode modérateur"),
                        Component.text("§8Accès aux outils de staff")
                )
        );
    }

    public ItemStack vanishButton() {
        return ItemBuilder.build(
                Material.BARRIER,
                "§5§lMode Vanish",
                List.of(
                        Component.text("§7Clique pour devenir invisible"),
                        Component.text("§8Les joueurs ne te verront plus")
                )
        );
    }


    public void createMainMenu(Player player) {
        Component title = Component.text("Menu Principal")
                .color(NamedTextColor.DARK_GRAY)
                .decorate(TextDecoration.BOLD);

        Inventory mainMenu = Bukkit.createInventory(null, 27, title);

        for (int i = 0; i < mainMenu.getSize(); i++) {
            mainMenu.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }

        mainMenu.setItem(10, playerList());
        mainMenu.setItem(13, modButton());
        mainMenu.setItem(16, vanishButton());

        player.openInventory(mainMenu);
    }

    public ItemStack playerHead(Player target) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta rawMeta = item.getItemMeta();

        if (!(rawMeta instanceof SkullMeta meta)) return item;

        meta.setPlayerProfile(target.getPlayerProfile());

        meta.displayName(Component.text("§b§l" + target.getName()));

        meta.lore(List.of(
                Component.text("§8Action staff")
        ));

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack teleportPlayer() {
        return ItemBuilder.build(
                Material.ENDER_PEARL,
                "§b§lTéléportation",
                List.of(
                        Component.text("§7Se téléporter au joueur"),
                        Component.text("§8Action staff rapide")
                )
        );
    }

    public ItemStack banPermPlayer() {
        return ItemBuilder.build(
                Material.IRON_AXE,
                "§4§lBan Définitif",
                List.of(
                        Component.text("§7Bannir définitivement le joueur"),
                        Component.text("§cAction irréversible")
                )
        );
    }

    public ItemStack mutePlayer() {
        return ItemBuilder.build(
                Material.ACACIA_HANGING_SIGN,
                "§e§lMute",
                List.of(
                        Component.text("§7Rendre le joueur muet"),
                        Component.text("§8Empêche le chat")
                )
        );
    }

    public ItemStack invseePlayer() {
        return ItemBuilder.build(
                Material.CHEST,
                "§a§lInventaire",
                List.of(
                        Component.text("§7Voir l'inventaire du joueur"),
                        Component.text("§8Inspection staff")
                )
        );
    }

    public ItemStack invseeEnderchestPlayer() {
        return ItemBuilder.build(
                Material.ENDER_CHEST,
                "§d§lEnder Chest",
                List.of(
                        Component.text("§7Voir l'ender chest du joueur"),
                        Component.text("§8Accès administrateur")
                )
        );
    }

    public ItemStack lookupPlayer() {
        return ItemBuilder.build(
                Material.BOOK,
                "§9§lInformations",
                List.of(
                        Component.text("§7Afficher les infos du joueur"),
                        Component.text("§8Stats / historique / etc.")
                )
        );
    }

    public ItemStack freezeButton() {
        return ItemBuilder.build(
                Material.BLUE_ICE,
                "§3§lFreeze",
                List.of(
                        Component.text("§7Bloquer les mouvements du joueur"),
                        Component.text("§bUtilisé en cas de suspicion")
                )
        );
    }


    public void createLookupMenu(Player mod, Player target) {
        Component title = Component.text("Inspection: " + target.getName())
                .color(NamedTextColor.DARK_GRAY)
                .decorate(TextDecoration.BOLD);

        Inventory lookupMenu = Bukkit.createInventory(null, 27, title);

        for (int i = 0; i < lookupMenu.getSize(); i++) {
            lookupMenu.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }

        lookupMenu.setItem(9, playerHead(target));
        lookupMenu.setItem(11, lookupPlayer());
        lookupMenu.setItem(12, teleportPlayer());
        lookupMenu.setItem(13, banPermPlayer());
        lookupMenu.setItem(14, mutePlayer());
        lookupMenu.setItem(15, invseePlayer());
        lookupMenu.setItem(16, invseeEnderchestPlayer());
        lookupMenu.setItem(17, freezeButton());

        mod.openInventory(lookupMenu);
    }


    public void allPlayersMenu(Player player) {
        Inventory playersMenu = Bukkit.createInventory(null, 54, Component.text("Joueurs en ligne"));

        for (Player joueur : Bukkit.getOnlinePlayers()) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            headMeta.setPlayerProfile(joueur.getPlayerProfile());
            headMeta.displayName(Component.text(joueur.getName()));

            head.setItemMeta(headMeta);
            playersMenu.addItem(head);
        }

        player.openInventory(playersMenu);
    }

    public void allPlayersTeleportMenu(Player player) {
        Inventory playersMenu = Bukkit.createInventory(null, 54, Component.text("Se téléporter sur un joueur"));

        for (Player joueur : Bukkit.getOnlinePlayers()) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            headMeta.setPlayerProfile(joueur.getPlayerProfile());
            headMeta.displayName(Component.text(joueur.getName()));

            head.setItemMeta(headMeta);
            playersMenu.addItem(head);
        }

        player.openInventory(playersMenu);
    }
}