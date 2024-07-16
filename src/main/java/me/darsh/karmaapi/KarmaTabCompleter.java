package me.darsh.karmaapi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KarmaTabCompleter implements TabCompleter {

    private static final List<String> COMMANDS = Arrays.asList("help", "point", "leaderboard", "config", "reload", "set", "restart");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], COMMANDS, new ArrayList<>());
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("point")) {
            return StringUtil.copyPartialMatches(args[1], Arrays.asList("punya", "pap"), new ArrayList<>());
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("leaderboard")) {
            return StringUtil.copyPartialMatches(args[1], Arrays.asList("punya", "pap"), new ArrayList<>());
        }

        return new ArrayList<>();
    }
}
