package net.natroutter.minicore.utilities;

import net.natroutter.minicore.MiniCore;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Utils {

    private static final Lang lang = MiniCore.getLang();

    public static ArrayList<String> playerNameList() {
        ArrayList<String> list = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            list.add(p.getName());
        }
        return list;
    }

    public static GameMode ValidateGamemode(String gm) {
        switch(gm.toLowerCase()) {
            case "s": case "0": case "survival":
                return GameMode.SURVIVAL;
            case "c": case "1": case "creative":
                return GameMode.CREATIVE;
            case "a": case "2": case "adventure":
                return GameMode.ADVENTURE;
            case "sp": case "3": case "spectator":
                return GameMode.SPECTATOR;
            default:
                return null;
        }
    }

    public static String getGamemodeName(GameMode gm) {
        switch(gm) {
            case SURVIVAL:
                return lang.GameModes.Survival;
            case CREATIVE:
                return lang.GameModes.Creative;
            case ADVENTURE:
                return lang.GameModes.Adventure;
            case SPECTATOR:
                return lang.GameModes.Spectator;
            default:
                return null;
        }
    }

    public static void FancyPluginMessage(JavaPlugin pl) {
        ConsoleCommandSender Console = pl.getServer().getConsoleSender();
        PluginDescriptionFile pdf = pl.getDescription();
        Console.sendMessage("§5                                                  ");
        Console.sendMessage("§5      __  __ _      _  ___                        ");
        Console.sendMessage("§5     |  \\/  (_)_ _ (_)/ __|___ _ _ ___            ");
        Console.sendMessage("§5     | |\\/| | | ' \\| | (__/ _ \\ '_/ -_)           ");
        Console.sendMessage("§5     |_|  |_|_|_||_|_|\\___\\___/_| \\___|           ");
        Console.sendMessage("§5                                                  ");
        Console.sendMessage("§5Loading version §d"+pdf.getVersion()+" §5on §d" + Bukkit.getServer().getName() + " §5- §d" + Bukkit.getServer().getVersion());
        Console.sendMessage("§d       Minicore created by §dNATroutter             ");
        Console.sendMessage("§5	                                                 ");
    }
}
