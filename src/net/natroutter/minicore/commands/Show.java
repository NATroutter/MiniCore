package net.natroutter.minicore.commands;

import com.j256.ormlite.stmt.query.In;
import jdk.jshell.execution.Util;
import me.clip.placeholderapi.PlaceholderAPI;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Database.handlers.PlayerDataHandler;
import net.natroutter.minicore.handlers.Database.tables.PlayerData;
import net.natroutter.minicore.handlers.Hooks;
import net.natroutter.minicore.handlers.features.InfoHandler;
import net.natroutter.minicore.utilities.*;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Show extends Command {

    private Lang lang = MiniCore.getLang();
    private Hooks hooks = MiniCore.getHooks();
    private Config config = MiniCore.getConf();
    private Utilities utils = MiniCore.getUtilities();

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
        return value ? lang.ToggleStates.enabled : lang.ToggleStates.disabled;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.OnlyIngame);
            return false;
        }

        BasePlayer p = BasePlayer.from(sender);

        if (args.length == 0) {
            p.sendMessage(lang.Prefix + lang.InvalidPlayer);

        } else if (args.length == 1) {
            if (p.hasPermission("minicore.show")) {

                BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[0])); //TODO add offlineplayer support
                if (target == null || !target.isOnline()) {
                    p.sendMessage(lang.Prefix + lang.InvalidPlayer);
                    return false;
                }
                PlayerData data = InfoHandler.updatePlayer(target);
                if (data==null) {return false;}

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
                    show.replaceAll("{lastplayed}", printDate(data.getFirstplayed()));
                    show.replaceAll("{bedlocation}", printLoc(data.getBedlocation()));
                    show.replaceAll("{flying}", printBool(data.getFlying()));
                    show.replaceAll("{allowfly}", printBool(data.getAllowedfly()));
                    show.replaceAll("{invehicle}", printBool(data.getInvehicle()));
                    show.replaceAll("{god}", printBool(data.getGod()));

                    if (hooks.PlaceHolderApi.isHooked()) {
                        show = StringHandler.from(show, PlaceholderAPI.setPlaceholders(p, show.build()));
                    }
                    show.send(p);
                }
            } else {
                p.sendMessage(lang.Prefix + lang.NoPerm);
            }
        } else {
            p.sendMessage(lang.Prefix + lang.ToomanyArgs);
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