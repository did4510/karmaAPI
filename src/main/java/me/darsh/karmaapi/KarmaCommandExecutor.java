package me.darsh.karmaapi;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KarmaCommandExecutor implements CommandExecutor {

    private final KarmaAPI plugin;

    public KarmaCommandExecutor(KarmaAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;

        // Handle commands here
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelpMessage(player);
        } else if (args[0].equalsIgnoreCase("point")) {
            handlePointCommand(player, args);
        } else if (args[0].equalsIgnoreCase("leaderboard")) {
            handleLeaderboardCommand(player, args);
        } else if (args[0].equalsIgnoreCase("config")) {
            plugin.getKarmaGui().openConfig(player);
        } else if (args[0].equalsIgnoreCase("reload")) {
            handleReloadCommand(player);
        } else if (args[0].equalsIgnoreCase("set")) {
            handleSetCommand(player, args);
        } else if (args[0].equalsIgnoreCase("restart")) {
            handleRestartCommand(player);
        } else {
            player.sendMessage(ChatColor.RED + "Unknown command. Use /karma help for a list of commands.");
        }

        return true;
    }

    private void sendHelpMessage(Player player) {
        player.sendMessage(ChatColor.GREEN + "/karma help - Show all Karma commands");
        player.sendMessage(ChatColor.GREEN + "/karma point punya - Show your Punya points");
        player.sendMessage(ChatColor.GREEN + "/karma point pap - Show your Pap points");
        player.sendMessage(ChatColor.GREEN + "/karma leaderboard punya - Show Punya leaderboard");
        player.sendMessage(ChatColor.GREEN + "/karma leaderboard pap - Show Pap leaderboard");
        player.sendMessage(ChatColor.GREEN + "/karma config - Open config GUI");
        player.sendMessage(ChatColor.GREEN + "/karma reload - Reload the plugin");
        player.sendMessage(ChatColor.GREEN + "/karma set <player> <punya/pap> <amount> - Set points for a player");
        player.sendMessage(ChatColor.GREEN + "/karma restart - Restart the points system");
    }

    private void handlePointCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /karma point <punya/pap>");
            return;
        }

        String type = args[1];
        if (type.equalsIgnoreCase("punya")) {
            int points = plugin.getDatabaseManager().getPoints(player.getUniqueId(), "punya");
            player.sendMessage(ChatColor.GREEN + "You have " + points + " Punya points.");
        } else if (type.equalsIgnoreCase("pap")) {
            int points = plugin.getDatabaseManager().getPoints(player.getUniqueId(), "pap");
            player.sendMessage(ChatColor.RED + "You have " + points + " Pap points.");
        } else {
            player.sendMessage(ChatColor.RED + "Unknown point type. Use 'punya' or 'pap'.");
        }
    }

    private void handleLeaderboardCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /karma leaderboard <punya/pap>");
            return;
        }

        String type = args[1];
        if (type.equalsIgnoreCase("punya")) {
            plugin.getKarmaGui().openLeaderboard(player, "punya");
        } else if (type.equalsIgnoreCase("pap")) {
            plugin.getKarmaGui().openLeaderboard(player, "pap");
        } else {
            player.sendMessage(ChatColor.RED + "Unknown leaderboard type. Use 'punya' or 'pap'.");
        }
    }

    private void handleReloadCommand(Player player) {
        plugin.reloadConfig();
        player.sendMessage(ChatColor.GREEN + "karmaapi configuration reloaded.");
    }

    private void handleSetCommand(Player player, String[] args) {
        if (args.length < 4) {
            player.sendMessage(ChatColor.RED + "Usage: /karma set <player> <punya/pap> <amount>");
            return;
        }

        Player target = plugin.getServer().getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        String type = args[2];
        int amount;
        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Amount must be a number.");
            return;
        }

        plugin.getDatabaseManager().setPoints(target.getUniqueId(), type, amount);
        player.sendMessage(ChatColor.GREEN + "Set " + amount + " " + type + " points for " + target.getName() + ".");
    }

    private void handleRestartCommand(Player player) {
        plugin.getDatabaseManager().resetPoints();
        player.sendMessage(ChatColor.GREEN + "Karma points system has been restarted.");
    }
}
