package me.darsh.karmaapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager {

    private final KarmaAPI plugin;
    private Connection connection;

    public DatabaseManager(KarmaAPI plugin) {
        this.plugin = plugin;
    }

    public void openConnection() {
        try {
            if (plugin.getConfig().getString("database.type").equalsIgnoreCase("sqlite")) {
                connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/" + plugin.getConfig().getString("database.sqlite.file"));
            } else if (plugin.getConfig().getString("database.type").equalsIgnoreCase("mysql")) {
                String host = plugin.getConfig().getString("database.mysql.host");
                int port = plugin.getConfig().getInt("database.mysql.port");
                String database = plugin.getConfig().getString("database.mysql.database");
                String username = plugin.getConfig().getString("database.mysql.username");
                String password = plugin.getConfig().getString("database.mysql.password");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to open database connection: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to close database connection: " + e.getMessage());
        }
    }

    public void setupDatabase() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS karma (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "uuid TEXT NOT NULL," +
                            "type TEXT NOT NULL," +
                            "points INTEGER NOT NULL)"
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to setup database: " + e.getMessage());
        }
    }

    public int getPoints(UUID uuid, String type) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT points FROM karma WHERE uuid = ? AND type = ?"
            );
            statement.setString(1, uuid.toString());
            statement.setString(2, type);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("points");
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to get points from database: " + e.getMessage());
        }
        return 0;
    }

    public void setPoints(UUID uuid, String type, int points) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "REPLACE INTO karma (uuid, type, points) VALUES (?, ?, ?)"
            );
            statement.setString(1, uuid.toString());
            statement.setString(2, type);
            statement.setInt(3, points);
            statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to set points in database: " + e.getMessage());
        }
    }

    public void resetPoints() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM karma");
            statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to reset points in database: " + e.getMessage());
        }
    }
}
