package fr.kilian.easyAdmin.managers;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.models.FreezeData;
import fr.kilian.easyAdmin.models.StaffLog;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static fr.kilian.easyAdmin.utils.MessagesFormats.*;

public class FreezeManager {

    private final Map<UUID, FreezeData> freezeData = new HashMap<>();

    public void freeze(@NonNull Player mod, @NonNull Player target, String reason) {

        if (isFrozen(target)) {
            mod.sendMessage(
                    Component.text(ALREADY_FREEZE.getMessage())
                            .color(NamedTextColor.RED)
            );
            return;
        }

        FreezeData data = new FreezeData(
                target.getUniqueId(),
                mod.getUniqueId(),
                reason
        );

        freezeData.put(target.getUniqueId(), data);

        Main.getInstance().getLogManager().logPunishment(mod, target, "FREEZE");
        new StaffLog(mod.getUniqueId(), target.getUniqueId(), "FREEZE", reason);

        mod.sendMessage(
                Component.text(PREFIX.getPrefix() + target.getName() + " a été freeze.")
        );

        target.sendMessage(
                Component.text("Vous avez été freeze par " + mod.getName() + ".")
        );

        target.sendMessage(
                Component.text(DONT_DISCONNECT.getMessage())
        );
    }

    public void unfreeze(@NonNull Player mod, @NonNull Player target) {

        if (!isFrozen(target)) {
            mod.sendMessage(
                    Component.text(PLAYER_NOT_FREEZE.getMessage())
            );
            return;
        }

        freezeData.remove(target.getUniqueId());

        Main.getInstance().getLogManager().logPunishment(mod, target, "UNFREEZE");
        new StaffLog(mod.getUniqueId(), target.getUniqueId(), "UNFREEZE", "");

        // message staff
        mod.sendMessage(
                Component.text(PREFIX.getPrefix() + target.getName() + " a été unfreeze.")
        );

        // message joueur
        target.sendMessage(
                Component.text(UNFREEZE_MESSAGE.getMessage())
        );
    }

    public void unfreeze(@NonNull Player target) {

        if (!isFrozen(target))
            return;

        freezeData.remove(target.getUniqueId());

        target.sendMessage(
                Component.text("Vous n'êtes plus freeze.")
        );
    }

    public void toggleFreeze(Player mod, Player target){
        if(isFrozen(target)){
            unfreeze(mod, target);
        } else {
            freeze(mod, target, "MODERATOR MODE");
        }
    }

    public boolean isFrozen(@NonNull Player player) {
        return freezeData.containsKey(player.getUniqueId());
    }
}