package fr.kilian.easyAdmin.managers;

import fr.kilian.easyAdmin.models.FreezeData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FreezeManager {

    Map<UUID, FreezeData> freezeData = new HashMap<>();

    public void freeze(Player player){};

    public void unfreeze(Player player){};

    public boolean isFrozen(Player player){return false;};

}
