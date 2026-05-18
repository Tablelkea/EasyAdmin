package fr.kilian.easyAdmin.managers;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
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
import java.util.UUID;

public class InventoryManager {

    public ItemStack playerList() {
        ItemStack playerList = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = playerList.getItemMeta();
        meta.displayName(Component.text("Joueurs en ligne"));
        playerList.setItemMeta(meta);
        return playerList;
    }

    public ItemStack modButton() {
        ItemStack modButton = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = modButton.getItemMeta();
        meta.displayName(Component.text("Passer en mode Modérateur"));
        modButton.setItemMeta(meta);
        return modButton;
    }

    public ItemStack vanishButton() {
        ItemStack vanishButton = new ItemStack(Material.BARRIER);
        ItemMeta meta = vanishButton.getItemMeta();
        meta.displayName(Component.text("Passer en mode Vanish"));
        vanishButton.setItemMeta(meta);
        return vanishButton;
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
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
        meta.setPlayerProfile(target.getPlayerProfile());
        meta.displayName(Component.text(target.getName()));
        playerHead.setItemMeta(meta);
        return playerHead;
    }

    public ItemStack teleportPlayer() {
        ItemStack teleportPlayer = new ItemStack(Material.ENDER_PEARL);
        ItemMeta meta = teleportPlayer.getItemMeta();
        meta.displayName(Component.text("Se téléporter au joueur"));
        teleportPlayer.setItemMeta(meta);
        return teleportPlayer;
    }

    public ItemStack banPermPlayer() {
        ItemStack banPermPlayer = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = banPermPlayer.getItemMeta();
        meta.displayName(Component.text("Bannir définitivement le joueur"));
        banPermPlayer.setItemMeta(meta);
        return banPermPlayer;
    }

    public ItemStack mutePlayer() {
        ItemStack mutePlayer = new ItemStack(Material.ACACIA_HANGING_SIGN);
        ItemMeta meta = mutePlayer.getItemMeta();
        meta.displayName(Component.text("Mute le joueur"));
        mutePlayer.setItemMeta(meta);
        return mutePlayer;
    }

    public ItemStack invseePlayer() {
        ItemStack invseePlayer = new ItemStack(Material.CHEST);
        ItemMeta meta = invseePlayer.getItemMeta();
        meta.displayName(Component.text("Ouvrir l'inventaire"));
        invseePlayer.setItemMeta(meta);
        return invseePlayer;
    }

    public ItemStack invseeEnderchestPlayer() {
        ItemStack invseeEnderchestPlayer = new ItemStack(Material.ENDER_CHEST);
        ItemMeta meta = invseeEnderchestPlayer.getItemMeta();
        meta.displayName(Component.text("Ouvrir l'ender chest"));
        invseeEnderchestPlayer.setItemMeta(meta);
        return invseeEnderchestPlayer;
    }

    public ItemStack lookupPlayer() {
        ItemStack lookupPlayer = new ItemStack(Material.BOOK);
        ItemMeta meta = lookupPlayer.getItemMeta();
        meta.displayName(Component.text("Afficher les informations"));
        lookupPlayer.setItemMeta(meta);
        return lookupPlayer;
    }

    public ItemStack freezeButton() {
        ItemStack freezeButton = new ItemStack(Material.BLUE_ICE);
        ItemMeta meta = freezeButton.getItemMeta();
        meta.displayName(Component.text("Freeze le joueur"));
        freezeButton.setItemMeta(meta);
        return freezeButton;
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


    public PlayerProfile createPlayerProfile(String textureUrl, String name) {
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), name);

        String json = "{\"textures\":{\"SKIN\":{\"url\":\"" + textureUrl + "\"}}}";
        String base64 = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));

        profile.setProperty(new ProfileProperty("textures", base64));
        return profile;
    }
}