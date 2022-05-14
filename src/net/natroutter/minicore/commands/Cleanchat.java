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

public class Cleanchat extends Command {

    private LangManager lang;
    private Effects effects;

    public Cleanchat(Handler handler) {
        super("Cleanchat");
        this.setAliases(Collections.singletonList("cc"));
        lang = handler.getLang();
        effects = handler.getEffects();
    }

    @Override
    public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
        if (!sender.hasPermission("minicore.cleanchat")) {
            lang.send(sender, Translations.Prefix, Translations.NoPerm);
            return false;
        }

        if (args.length == 0) {
            for (int i = 0; i < 255; i++) {
                Bukkit.broadcastMessage(" ");
            }
            StringHandler message = new StringHandler(lang.get(Translations.ChatCleaned)).setPrefix(lang.get(Translations.Prefix));

            if (sender instanceof Player p) {
                message.replaceAll("%player%", p.getName());
            } else {
                message.replaceAll("%player%", lang.get(Translations.ConsoleName));
            }

            for (Player onlineP : Bukkit.getOnlinePlayers()) {
                message.send(onlineP);
                effects.sound(onlineP, Sounds.Modified);
            }
        } else {
            lang.send(sender, Translations.Prefix, Translations.ToomanyArgs);
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }
}
