package fr.kilian.easyAdmin.commands;

import fr.kilian.easyAdmin.Main;
import fr.kilian.easyAdmin.managers.LookupManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LookupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        LookupManager lm = Main.getInstance().getLookupManager();

        Player target;

        // /lookup sans argument
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Usage: /lookup <joueur>");
                return true;
            }
            target = player;
        } else {
            target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(
                        Component.text("Joueur introuvable.")
                                .color(NamedTextColor.RED)
                );
                return true;
            }
        }

        sender.sendMessage(Component.empty());

        sender.sendMessage(
                Component.text("══════ Profil de ", NamedTextColor.GOLD)
                        .append(Component.text(target.getName(), NamedTextColor.WHITE))
                        .append(Component.text(" ══════", NamedTextColor.GOLD))
        );

        sender.sendMessage(
                Component.text("Première connexion : ", NamedTextColor.GRAY)
                        .append(Component.text(
                                lm.getFirstLogin(target),
                                NamedTextColor.WHITE
                        ))
        );

        sender.sendMessage(
                Component.text("Dernière connexion : ", NamedTextColor.GRAY)
                        .append(Component.text(
                                lm.getLastLogin(target),
                                NamedTextColor.WHITE
                        ))
        );

        sender.sendMessage(
                Component.text("Sanctions : ", NamedTextColor.GRAY)
                        .append(Component.text(
                                String.valueOf(lm.getHistory(target).size()),
                                NamedTextColor.RED
                        ))
        );

        var punishments = lm.getHistory(target);

        if (!punishments.isEmpty()) {
            sender.sendMessage(
                    Component.text("Historique :", NamedTextColor.GRAY)
            );

            for (var punishment : punishments) {
                sender.sendMessage(
                        Component.text("  • ", NamedTextColor.DARK_GRAY)
                                .append(Component.text(
                                        punishment.toString(),
                                        NamedTextColor.WHITE
                                ))
                );
            }
        }

        sender.sendMessage(
                Component.text(
                        "═══════════════════════════════",
                        NamedTextColor.GOLD
                )
        );

        sender.sendMessage(Component.empty());

        return true;
    }
}