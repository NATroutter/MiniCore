package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.minicore.objects.Particles;
import net.natroutter.minicore.objects.Sounds;

import net.natroutter.natlibs.handlers.langHandler.language.LangManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Top extends Command {

    private LangManager lang;
    private Effects effects;

    public Top(Handler handler) {
        super("Top");
        lang = handler.getLang();
        effects = handler.getEffects();
    }

    @Override
    public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
        if (!(sender instanceof Player p)) {
            lang.send(sender, Translations.Prefix, Translations.OnlyIngame);
            return false;
        }

        if (!sender.hasPermission("minicore.top")) {
            lang.send(sender, Translations.Prefix, Translations.NoPerm);
            return false;
        }

        if (args.length == 0) {

            effects.particle(p, Particles.TeleportStart);

            int highest = p.getWorld().getHighestBlockYAt(p.getLocation());
            Location ploc = p.getLocation();
            ploc.setY(highest+1);
            p.teleport(ploc);

            effects.particle(ploc, Particles.TeleportEnd);
            effects.sound(p, Sounds.Teleported);

            lang.send(p, Translations.Prefix, Translations.TeleportedToTop);

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


