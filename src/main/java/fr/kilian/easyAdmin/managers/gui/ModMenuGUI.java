package fr.kilian.easyAdmin.managers.gui;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.LookupManager;
import fr.kilian.easyAdmin.models.Punishment;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ModMenuGUI {

    public static final String PDC_ACTION_KEY   = "mod_gui_action";
    public static final String PDC_TARGET_KEY   = "mod_gui_target";

    public static final String ACTION_FREEZE    = "FREEZE";
    public static final String ACTION_INSPECT   = "INSPECT";
    public static final String ACTION_TELEPORT  = "TELEPORT";
    public static final String ACTION_LOOKUP    = "LOOKUP";

    private final NamespacedKey actionKey;
    private final NamespacedKey targetKey;

    public ModMenuGUI() {
        this.actionKey = new NamespacedKey(Main.getInstance(), PDC_ACTION_KEY);
        this.targetKey = new NamespacedKey(Main.getInstance(), PDC_TARGET_KEY);
    }

    public void open(Player moderator, Player target) {
        Inventory inv = Bukkit.createInventory(null, 27,
                Component.text("Modération › ", NamedTextColor.DARK_GRAY)
                        .append(Component.text(target.getName(), NamedTextColor.WHITE)));

        String targetName = target.getName();

        inv.setItem(0, buildHead(target));

        boolean frozen = Main.getInstance().getFreezeManager().isFrozen(target);
        inv.setItem(2, buildButton(
                frozen ? Material.PACKED_ICE : Material.ICE,
                frozen
                        ? Component.text("❄ Dégeler", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)
                        : Component.text("❄ Freeze", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                List.of(frozen
                        ? Component.text("Ce joueur est actuellement gelé.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                        : Component.text("Immobilise le joueur avec blindness.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
                ACTION_FREEZE, targetName
        ));

        inv.setItem(4, buildButton(
                Material.CHEST,
                Component.text("🔍 Inspecter l'inventaire", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                List.of(Component.text("Ouvre l'inventaire de " + targetName + ".", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
                ACTION_INSPECT, targetName
        ));

        inv.setItem(6, buildButton(
                Material.ENDER_PEARL,
                Component.text("🧭 Se téléporter", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                List.of(Component.text("Vous téléporte jusqu'à " + targetName + ".", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
                ACTION_TELEPORT, targetName
        ));

        inv.setItem(8, buildLookupButton(target, targetName));

        fillGlass(inv);

        moderator.openInventory(inv);
    }


    private ItemStack buildHead(Player target) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(target);
        meta.displayName(Component.text(target.getName(), NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty());
        lore.add(Component.text("Ping : ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(target.getPing() + " ms", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("Gamemode : ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(target.getGameMode().name(), NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)));
        meta.lore(lore);
        skull.setItemMeta(meta);
        return skull;
    }

    private ItemStack buildLookupButton(Player target, String targetName) {
        LookupManager lm = Main.getInstance().getLookupManager();
        List<Punishment> punishments = lm.getHistory(target);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty());
        lore.add(Component.text("Première connexion : ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(lm.getFirstLogin(target), NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("Dernière connexion : ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(lm.getLastLogin(target), NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("Sanctions : ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(punishments.size() + "", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.empty());
        lore.add(Component.text("Clic pour voir le profil complet.", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false));

        return buildButton(Material.PAPER,
                Component.text("📋 Lookup", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
                lore,
                ACTION_LOOKUP, targetName);
    }

    private ItemStack buildButton(Material material, Component name, List<Component> lore,
                                  String action, String targetName) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(name);
        meta.lore(lore);
        meta.getPersistentDataContainer().set(actionKey, PersistentDataType.STRING, action);
        meta.getPersistentDataContainer().set(targetKey, PersistentDataType.STRING, targetName);
        item.setItemMeta(meta);
        return item;
    }

    private void fillGlass(Inventory inv) {
        ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = glass.getItemMeta();
        meta.displayName(Component.text(" ").decoration(TextDecoration.ITALIC, false));
        glass.setItemMeta(meta);
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) inv.setItem(i, glass);
        }
    }

    public NamespacedKey getActionKey() { return actionKey; }
    public NamespacedKey getTargetKey()  { return targetKey; }
}