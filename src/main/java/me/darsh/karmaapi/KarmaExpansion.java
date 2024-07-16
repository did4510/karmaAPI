package me.darsh.karmaapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class KarmaExpansion extends PlaceholderExpansion {

    private final KarmaAPI plugin;

    public KarmaExpansion(KarmaAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "karma";
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;  // Set to true if you want this to persist through server restarts
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("punya")) {
            return String.valueOf(plugin.getDatabaseManager().getPoints(player.getUniqueId(), "punya"));
        }

        if (identifier.equals("pap")) {
            return String.valueOf(plugin.getDatabaseManager().getPoints(player.getUniqueId(), "pap"));
        }

        return null;  // Placeholder is not handled by this expansion
    }
}
