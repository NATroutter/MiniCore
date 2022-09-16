package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.*;

import net.natroutter.natlibs.handlers.langHandler.language.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Night extends Command {

    private LangManager lang;
    private Effects effects;
    private Utils utils;

    public Night(Handler handler) {
        super("Night");
        lang = handler.getLang();
        effects = handler.getEffects();
        utils = handler.getUtils();
    }

    @Override
    public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
        if (!sender.hasPermission("minicore.night")) {
            lang.send(sender, Translations.Prefix, Translations.NoPerm);
            return false;
        }

        if (args.length == 0) {

            if (sender instanceof Player p) {
                p.getWorld().setTime(14000);
                lang.send(p, Translations.Prefix, Translations.TimeSetToNight);
                effects.sound(p, Sounds.Modified);
            } else {
                lang.send(sender, Translations.Prefix, Translations.WorldNeeded);
            }
        } else if (args.length == 1) {
            World world = Bukkit.getWorld(args[0]);
            if (world == null) {
                lang.send(sender, Translations.Prefix, Translations.InvalidWorld);
                return false;
            }
            world.setTime(14000);
            lang.send(sender, Translations.Prefix, Translations.TimeSetToNight);

        } else {
            lang.send(sender, Translations.Prefix, Translations.ToomanyArgs);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            List<String> shorted = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], utils.worldNameList(), shorted);
            Collections.sort(shorted);
            return shorted;
        }
        return null;
    }
}
