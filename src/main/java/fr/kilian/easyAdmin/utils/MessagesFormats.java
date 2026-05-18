package fr.kilian.easyAdmin.utils;

public enum MessagesFormats {

    PREFIX("§8[§cAdministration§8] §7"),

    // Help commands
    BAN_HELP("§eUsage : /ban <joueur> <raison>"),
    FREEZE_HELP("§eUsage : /freeze <joueur> [raison]"),
    LOOKUP_HELP("§eUsage : /lookup <joueur>"),
    MUTE_HELP("§eUsage : /mute <joueur> <minutes> <raison>"),
    PERMISSION_GIVE_HELP("§eUsage : /perm give <joueur> <permission>"),
    PERMISSION_TEMP_HELP("§eUsage : /perm temp <joueur> <permission> <minutes>"),
    PERMISSION_REMOVE_HELP("§eUsage : /perm remove <joueur> <permission>"),
    PERMISSION_LIST_HELP("§eUsage : /perm list <joueur>"),
    TEMP_BAN_HELP("§eUsage : /tempban <joueur> <jours> <raison>"),
    UNBAN_HELP("§eUsage : /unban <joueur>"),
    UNFREEZE_HELP("§eUsage : /unfreeze <joueur>"),
    UNMUTE_HELP("§eUsage : /unmute <joueur>"),
    WARN_HELP("§eUsage : /warn <joueur> <raison>"),

    // General errors
    PLAYER_NOT_FOUND("§cJoueur introuvable."),
    AVAILABLE_ONLY_INGAME("§cCette commande est uniquement utilisable en jeu."),
    BAD_TIME_ARGUMENT("§cDurée invalide."),
    NO_ONLINE_PLAYER("§cAucun joueur connecté pour exécuter cette commande."),
    DONT_HAVE_PERMISSION("§cPermission insuffisante."),
    NO_REASON("Aucune raison"),

    // Self actions
    CANNOT_SELF_FREEZE("§cVous ne pouvez pas vous freeze vous-même."),
    CANNOT_SELF_BAN("§cVous ne pouvez pas vous bannir vous-même."),
    CANNOT_SELF_MUTE("§cVous ne pouvez pas vous mute vous-même."),
    CANNOT_SELF_TEMPBAN("§cVous ne pouvez pas vous bannir temporairement vous-même."),
    CANNOT_SELF_WARN("§cVous ne pouvez pas vous avertir vous-même."),
    CANNOT_SELF_SANCTION("§cVous ne pouvez pas vous sanctionner vous-même."),
    CANNOT_SELF_TARGET("§cVous ne pouvez pas vous cibler vous-même."),

    // Player states
    PLAYER_ALREADY_BAN("§cCe joueur est déjà banni."),
    PLAYER_ALREADY_TEMPBAN("§cCe joueur est déjà banni temporairement."),
    PLAYER_ALREADY_FREEZE("§cCe joueur est déjà freeze."),
    PLAYER_NOT_BANNED("§cCe joueur n'est pas banni."),
    PLAYER_NOT_FREEZE("§cCe joueur n'est pas freeze."),
    PLAYER_NOT_MUTE("§cCe joueur n'est pas mute."),
    ALREADY_FREEZE("§cCe joueur est déjà freeze."),

    // Warnings
    DONT_DISCONNECT("§6Ne vous déconnectez pas. Attendez un membre du staff."),

    // Permissions
    NO_PERMISSION("§7Aucune permission."),
    PERMISSION_REMOVE("§aPermission retirée avec succès."),
    PERMISSION_GIVE("§aPermission ajoutée avec succès."),
    TEMPORARY_PERMISSION_GIVE("§aPermission temporaire ajoutée avec succès."),

    // Modes
    SWITCH_TO_MODERATOR_MODE("§aVous êtes maintenant en mode modérateur."),
    LEAVE_MODERATOR_MODE("§cVous avez quitté le mode modérateur."),
    SWITCH_TO_VANISH("§aVous êtes désormais en mode vanish."),
    DISABLE_VANISH("§cVous êtes désormais visible par tous."),
    IN_VANISH("§7Vous êtes actuellement en mode vanish."),

    // Sanctions
    PERMANENT_BAN_MESSAGE("§cVous avez été banni définitivement de ce serveur."),
    BAN_MESSAGE("§cVous êtes banni de ce serveur."),
    FREEZE_MESSAGE("§bVous êtes freeze, vous ne pouvez plus bouger."),
    UNFREEZE_MESSAGE("§aVous n'êtes plus freeze."),
    MUTE_MESSAGE("§cVous êtes actuellement réduit au silence."),
    UNMUTE_MESSAGE("§aVous n'êtes plus réduit au silence.");

    private final String message;

    MessagesFormats(String message) {
        this.message = message;
    }

    public String getPrefix(){
        return PREFIX.message;
    }

    public String getMessage() {
        return PREFIX.message + message;
    }
}