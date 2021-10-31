package net.natroutter.minicore.commands;

import com.j256.ormlite.misc.IOUtils;
import com.j256.ormlite.stmt.query.In;
import jdk.jshell.execution.Util;
import me.clip.placeholderapi.PlaceholderAPI;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Database.handlers.PlayerDataHandler;
import net.natroutter.minicore.handlers.Database.tables.PlayerData;
import net.natroutter.minicore.handlers.Hooks;
import net.natroutter.minicore.handlers.features.InfoHandler;
import net.natroutter.minicore.utilities.*;

import net.natroutter.natlibs.objects.MojangApiInfo;
import net.natroutter.natlibs.utilities.MojangAPI;
import net.natroutter.natlibs.utilities.StringHandler;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Show extends Command {

    private Lang lang = MiniCore.getLang();
    private Hooks hooks = MiniCore.getHooks();
    private Config config = MiniCore.getConf();
    private Utilities utils = MiniCore.getUtilities();
    private MojangAPI mojangAPI = MiniCore.getMojangAPI();
    private final PlayerDataHandler pdh = MiniCore.getDataHandler();

    public Show() {
        super("");
    }

    public String printLoc(String serializedLoc) {
        Location loc = utils.deserializeLocation(serializedLoc, InfoHandler.sep);
        if (loc != null) {
            StringHandler locF = new StringHandler(lang.LocationFormat);
            if (loc.getWorld() == null) {
                locF.replaceAll("{world}", lang.Unknown);
            } else {
                locF.replaceAll("{world}", loc.getWorld().getName());
            }
            locF.replaceAll("{x}", loc.getBlockX());
            locF.replaceAll("{y}", loc.getBlockY());
            locF.replaceAll("{z}", loc.getBlockZ());
            locF.replaceAll("{pitch}", loc.getPitch());
            locF.replaceAll("{yaw}", loc.getYaw());
            return locF.build();
        }
        return lang.Unknown;
    }

    public String printDate(Long value) {
        DateFormat simple = new SimpleDateFormat(config.DateFormat);
        Date date = new Date(value);
        return simple.format(date);
    }

    public String printGamemode(String gamemodeStr) {
        GameMode gm = Utils.ValidateGamemode(gamemodeStr);
        if (gm != null) {
            return Utils.getGamemodeName(gm);
        }
        return gamemodeStr;
    }

    public String printBool(Boolean value) {
        return value ? lang.Statues.yes : lang.Statues.no;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(lang.Prefix + lang.InvalidPlayer);

        } else if (args.length == 1) {
            if (sender.hasPermission("minicore.show")) {

                PlayerData data;
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null && target.isOnline()) {
                    data = InfoHandler.updatePlayer(target);
                } else {
                    data = pdh.get(mojangAPI.getUUID(args[0]));
                }
                if (data==null) {
                    sender.sendMessage(lang.Prefix + lang.InvalidPlayer);
                    return false;
                }

                for (String line : lang.ShowFormat) {
                    StringHandler show = new StringHandler(line);
                    show.replaceAll("{uuid}", data.getUuid());
                    show.replaceAll("{name}", data.getName());
                    show.replaceAll("{displayname}", data.getDisplayname());
                    show.replaceAll("{online}", printBool(data.getOnline()));
                    show.replaceAll("{ip}", data.getIp());
                    show.replaceAll("{lastloc}", printLoc(data.getLastloc()));
                    show.replaceAll("{locale}", data.getLocale());
                    show.replaceAll("{health}", data.getHealth());
                    show.replaceAll("{maxhealth}", data.getMaxhealth());
                    show.replaceAll("{food}", data.getFood());
                    show.replaceAll("{maxfood}", "20");
                    show.replaceAll("{level}", data.getLevel());
                    show.replaceAll("{gamemode}", printGamemode(data.getGamemode()));
                    show.replaceAll("{flyspeed}", data.getFlyspeed());
                    show.replaceAll("{walkspeed}", data.getWalkspeed());
                    show.replaceAll("{world}", data.getWorld());
                    show.replaceAll("{firstplayed}", printDate(data.getFirstplayed()));
                    show.replaceAll("{lastplayed}", printDate(data.getLastplayed()));
                    show.replaceAll("{bedlocation}", printLoc(data.getBedlocation()));
                    show.replaceAll("{flying}", printBool(data.getFlying()));
                    show.replaceAll("{allowfly}", printBool(data.getAllowedfly()));
                    show.replaceAll("{invehicle}", printBool(data.getInvehicle()));
                    show.replaceAll("{god}", printBool(data.getGod()));

                    if (hooks.PlaceHolderApi.isHooked()) {
                        if (target != null && target.isOnline()) {
                            show = StringHandler.from(show, PlaceholderAPI.setPlaceholders(target, show.build()));
                        }
                    }
                    sender.sendMessage(show.build());
                }
            } else {
                sender.sendMessage(lang.Prefix + lang.NoPerm);
            }
        } else {
            sender.sendMessage(lang.Prefix + lang.ToomanyArgs);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return Utils.playerNameList();
        }
        return null;
    }
}