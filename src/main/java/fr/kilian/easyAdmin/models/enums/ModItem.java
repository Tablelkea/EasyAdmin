package fr.kilian.easyAdmin.models.enums;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public enum ModItem {

    FREEZE(0, Material.BLAZE_ROD,
            Component.text("❄ Freeze", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
            Component.text("Clic droit sur un joueur pour le freeze.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),

    INSPECT(1, Material.CHEST,
            Component.text("🔍 Inspecter l'inventaire", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
            Component.text("Clic droit sur un joueur pour voir son inventaire.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),

    TELEPORT(2, Material.COMPASS,
            Component.text("🧭 Téléportation", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
            Component.text("Clic droit sur un joueur pour vous téléporter à lui.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),

    VANISH(3, Material.POTION,
            Component.text("👁 Vanish", NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
            Component.text("Clic droit pour activer/désactiver le vanish.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),

    LOOKUP(4, Material.BOOK,
            Component.text("📋 Global Lookup", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
            Component.text("Clic droit pour voir tous les joueurs disponible.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),

    LOOKUP_CLICKED_PLAYER(5, Material.STICK,
                          Component.text("👤 Lookup", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
            Component.text("Clic droit sur un joueur pour voir son profil.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),

    ENDERCHEST(5, Material.ENDER_CHEST,
            Component.text("🛅 Inspecter l'ender chest", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false),
            Component.text("Clic droit sur un joueur pour voir son enderchest", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));

    public static final String PDC_KEY = "mod_item_type";

    private final int slot;
    private final Material material;
    private final Component displayName;
    private final Component lore;

    ModItem(int slot, Material material, Component displayName, Component lore) {
        this.slot = slot;
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
    }

    public ItemStack build(NamespacedKey key) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(displayName);
        meta.lore(java.util.List.of(lore));
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, this.name());
        item.setItemMeta(meta);
        return item;
    }

    public int getSlot() { return slot; }
    public Material getMaterial() { return material; }
    public Component getDisplayName() { return displayName; }
}