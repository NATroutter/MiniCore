package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.natlibs.handlers.LangHandler.language.LangManager;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class Broadcast extends Command {

    private LangManager lang;
    private Config config;
    private Effects effects;

    public Broadcast(Handler handler) {
        super("Broadcast");
        lang = handler.getLang();
        config = handler.getConfig();
        effects = handler.getEffects();
    }

    @Override
    public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
        if (!sender.hasPermission("minicore.broadcast")) {
            lang.send(sender, Translations.Prefix, Translations.NoPerm);
            return false;
        }

        if (args.length == 0) {
            lang.send(sender, Translations.Prefix, Translations.InvalidBroadcastMessage);

        } else {
            StringHandler message = new StringHandler(args, ' ');
            if (sender.hasPermission("minicore.broadcast.colors")) {
                message.replaceColors();
            }

            StringHandler broadcast = new StringHandler(config.BroadcastFormat);
            broadcast.replaceAll("%message%", message.build());
            for (Player onlineP : Bukkit.getOnlinePlayers()) {
                broadcast.send(onlineP);
                effects.sound(onlineP, Sounds.Broadcast);
            }
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }
}

