package me.darsh.karmaapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class KarmaGui {

    private final KarmaAPI plugin;

    public KarmaGui(KarmaAPI plugin) {
        this.plugin = plugin;
    }

    public void openLeaderboard(Player player, String type) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GREEN + type.toUpperCase() + " Leaderboard");

        // Add leaderboard items to the inventory
        // Example: Add player heads or other items representing leaderboard entries
        // You can loop through top players and add their heads or other relevant information

        player.openInventory(inv);
    }

    public void openConfig(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Karma Config");

        // Add configuration items to the inventory
        // Example: Add clickable items to configure plugin settings

        player.openInventory(inv);
    }
}
