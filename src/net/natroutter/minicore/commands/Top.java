package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Top extends Command {

    public Top() {
        super("");
        this.setPermission("minicore.top");
        this.setPermissionMessage(lang.NoPerm);
    }

    private final Lang lang = MiniCore.getLang();
    private final Config config = MiniCore.getConf();

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.OnlyIngame);
            return false;
        }
        Player p = (Player)sender;

        if (args.length == 0) {

            Effect.particle(Settings.Particle.teleportStart(p.getLocation()));

            int highest = p.getWorld().getHighestBlockYAt(p.getLocation());
            Location ploc = p.getLocation();
            ploc.setY(highest+1);
            p.teleport(ploc);

            Effect.particle(Settings.Particle.teleportEnd(ploc));
            Effect.sound(p, Settings.Sound.teleported());
            p.sendMessage(lang.Prefix + lang.TeleportedToTop);

        } else {
            p.sendMessage(lang.Prefix + lang.ToomanyArgs);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }
}


