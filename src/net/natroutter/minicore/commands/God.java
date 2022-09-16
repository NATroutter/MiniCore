package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.objects.Particles;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.*;

import net.natroutter.natlibs.handlers.database.YamlDatabase;
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

public class God extends Command {

    private LangManager lang;
    private Effects effects;
    private YamlDatabase database;
    private Utils utils;

    public God(Handler handler) {
        super("God");
        lang = handler.getLang();
        effects = handler.getEffects();
        database = handler.getYamlDatabase();
        utils = handler.getUtils();
    }

    @Override
    public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player p)) {
                lang.send(sender, Translations.Prefix, Translations.InvalidArgs);
                return false;
            }

            if (p.hasPermission("minicore.god")) {
                boolean newstate = !database.getBoolean(p, "God");
                database.save(p, "God", newstate);

                StringHandler message = new StringHandler(lang.get(Translations.GodToggled)).setPrefix(lang.get(Translations.Prefix));
                message.replaceAll("%status%", newstate ? lang.get(Translations.ToggleStates_Enabled) : lang.get(Translations.ToggleStates_Disabled));
                message.send(p);
                effects.sound(p, Sounds.God);
                effects.particle(p, Particles.God);
            } else {
                lang.send(p, Translations.Prefix, Translations.NoPerm);
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("minicore.god.other")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null || !target.isOnline()) {
                    lang.send(sender, Translations.Prefix, Translations.InvalidPlayer);
                    return false;
                }
                boolean newstate = !database.getBoolean(target, "God");
                database.save(target, "God", newstate);

                StringHandler message = new StringHandler(lang.get(Translations.GodToggledOther)).setPrefix(lang.get(Translations.Prefix));
                message.replaceAll("%status%", newstate ? lang.get(Translations.ToggleStates_Enabled) : lang.get(Translations.ToggleStates_Disabled));
                message.replaceAll("%player%", target.getName());

                if (sender instanceof Player p) {
                    message.send(p);
                    if (!target.getUniqueId().equals(p.getUniqueId())) {
                        effects.sound(target, Sounds.God);
                        effects.sound(p, Sounds.God);
                        effects.particle(target, Particles.God);
                    } else {
                        effects.sound(p, Sounds.God);
                        effects.particle(target, Particles.God);
                    }
                } else {
                    sender.sendMessage(message.build());
                    effects.sound(target, Sounds.God);
                    effects.particle(target, Particles.God);
                }
            } else {
                lang.send(sender, Translations.Prefix, Translations.NoPerm);
            }

        } else {
            lang.send(sender, Translations.Prefix, Translations.ToomanyArgs);
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