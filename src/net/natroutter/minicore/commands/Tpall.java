package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.minicore.objects.Sounds;

import net.natroutter.natlibs.handlers.LangHandler.language.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Tpall extends Command {

    private LangManager lang;
    private Config config;
    private Effects effects;

    public Tpall(Handler handler) {
        super("Tpall");
        lang = handler.getLang();
        config = handler.getConfig();
        effects = handler.getEffects();
    }


    @Override
    public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
        if (!(sender instanceof Player p)) {
            lang.send(sender, Translations.Prefix, Translations.OnlyIngame);
            return false;
        }

        if (!sender.hasPermission("minicore.tpall")) {
            lang.send(sender, Translations.Prefix, Translations.NoPerm);
            return false;
        }

        if (args.length == 0) {
            for (Player onlineP : Bukkit.getOnlinePlayers()) {
                onlineP.teleport(p);
                effects.sound(onlineP, Sounds.Teleported);
            }
            lang.send(p, Translations.Prefix, Translations.AllPlayersTeleported);
        } else {
            lang.send(p, Translations.Prefix, Translations.ToomanyArgs);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }
}
