package me.darsh.karmaapi;

import org.bukkit.plugin.java.JavaPlugin;

public final class KarmaAPI extends JavaPlugin {

    private DatabaseManager databaseManager;
    private KarmaGui karmaGui;

    @Override
    public void onEnable() {
        getLogger().info("KarmaAPI has been enabled!");

        // Ensure the config is loaded and saved if not exist
        saveDefaultConfig();

        // Initialize DatabaseManager
        databaseManager = new DatabaseManager(this);
        databaseManager.openConnection();
        databaseManager.setupDatabase();

        // Initialize GUI handler
        karmaGui = new KarmaGui(this);

        // Register commands
        getCommand("karma").setExecutor(new KarmaCommandExecutor(this));
        getCommand("karma").setTabCompleter(new KarmaTabCompleter());

        // Register event listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        // Register PlaceholderAPI expansion if PlaceholderAPI is enabled
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new KarmaExpansion(this).register();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("KarmaAPI has been disabled.");

        // Cleanup database connection
        if (databaseManager != null) {
            databaseManager.closeConnection();
        }
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public KarmaGui getKarmaGui() {
        return karmaGui;
    }
}
