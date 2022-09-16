package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.*;
import net.natroutter.natlibs.handlers.langHandler.language.LangManager;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cleaninventory extends Command {

    private LangManager lang;
    private Effects effects;
    private Utils utils;

    public Cleaninventory(Handler handler) {
        super("Cleaninventory");
        this.setAliases(Collections.singletonList("ci"));
        lang = handler.getLang();
        effects = handler.getEffects();
        utils = handler.getUtils();
    }

    @Override
    public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
        if (!sender.hasPermission("minicore.Cleaninventory")) {
            lang.send(sender, Translations.Prefix, Translations.NoPerm);
            return false;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player p)) {
                lang.send(sender, Translations.Prefix, Translations.InvalidArgs);
                return false;
            }

            if (p.hasPermission("minicore.cleaninventory")) {
                p.getInventory().clear();
                lang.send(p, Translations.Prefix, Translations.InventoryCleaned);
                effects.sound(p, Sounds.Modified);
            } else {
                lang.send(p, Translations.Prefix, Translations.NoPerm);
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("minicore.cleaninventory.other")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null || !target.isOnline()) {
                    lang.send(sender, Translations.Prefix, Translations.InvalidPlayer);
                    return false;
                }

                if (sender instanceof Player p) {
                    if (!target.getUniqueId().equals(p.getUniqueId())) {
                        target.getInventory().clear();

                        StringHandler message = new StringHandler(lang.get(Translations.InventoryCleanedOther)).setPrefix(lang.get(Translations.Prefix));
                        message.replaceAll("%player%", target.getName());
                        message.send(p);
                        effects.sound(target, Sounds.Modified);
                        effects.sound(p, Sounds.Modified);
                    } else {
                        p.getInventory().clear();
                        lang.send(p, Translations.Prefix, Translations.InventoryCleaned);
                        effects.sound(p, Sounds.Modified);
                    }
                } else {
                    target.getInventory().clear();

                    StringHandler message = new StringHandler(lang.get(Translations.InventoryCleanedOther)).setPrefix(lang.get(Translations.Prefix));
                    message.replaceAll("%player%", target.getName());
                    sender.sendMessage(message.build());
                    effects.sound(target, Sounds.Modified);
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
