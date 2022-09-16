package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.minicore.objects.Particles;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.*;

import net.natroutter.natlibs.handlers.langHandler.language.LangManager;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tphere extends Command {

    private LangManager lang;
    private Config config;
    private Utils utils;
    private Effects effects;

    public Tphere(Handler handler) {
        super("Tphere");
        lang = handler.getLang();
        config = handler.getConfig();
        utils = handler.getUtils();
        effects = handler.getEffects();
    }

    @Override
    public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
        if (!(sender instanceof Player p)) {
            lang.send(sender, Translations.Prefix, Translations.OnlyIngame);
            return false;
        }

        if (!p.hasPermission("minicore.tphere")) {
            lang.send(p, Translations.Prefix, Translations.NoPerm);
            return false;
        }

        if (args.length == 0) {
            lang.send(p, Translations.Prefix, Translations.InvalidPlayer);
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                lang.send(p, Translations.Prefix, Translations.InvalidPlayer);
                return false;
            }
            if (!target.getUniqueId().equals(p.getUniqueId())) {
                effects.particle(target, Particles.TeleportStart);


                target.teleport(p.getLocation());

                effects.particle(p, Particles.TeleportEnd);
                effects.sound(p, Sounds.Teleported);
                effects.sound(target, Sounds.Teleported);

                StringHandler message = new StringHandler(lang.get(Translations.TeleportedToYou)).setPrefix(lang.get(Translations.Prefix));
                message.replaceAll("%player%", target.getName());
                message.send(p);
            } else {
                lang.send(p, Translations.Prefix, Translations.CantTargetYourSelf);
            }
        } else {
            lang.send(p, Translations.Prefix, Translations.ToomanyArgs);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            List<String> shorted = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], utils.playerNameList(), shorted);
            Collections.sort(shorted);
            return shorted;
        }
        return null;
    }
}
