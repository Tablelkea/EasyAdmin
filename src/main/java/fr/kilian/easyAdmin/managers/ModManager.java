package fr.kilian.easyAdmin.managers;

import fr.kilian.easyAdmin.models.ModSession;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class ModManager {

    Map<UUID, ItemStack[]> savedInventories = new HashMap<>();
    Set<UUID> modPlayers = new HashSet<>();
    Set<ItemStack> modItems = new HashSet<>();
    Map<UUID, ModSession> sessions;

    public void enableMod(Player player){};

    public void disableMod(Player player){};

    public void toggleMod(@NonNull Player player){};

    public boolean isInMod(@NonNull Player player){return false;};

    public void saveInventory(@NonNull Player player){};

    public void restoreInventory(@NonNull Player player){};

    public void giveModItems(@NotNull Player player){};

    public void clearModItems(@NotNull Player player){};

}
