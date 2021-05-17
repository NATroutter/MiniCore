package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.*;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Tphere extends Command {

    public Tphere() {
        super("");
        this.setPermission("minicore.tphere");
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
        BasePlayer p = BasePlayer.from(sender);

        if (args.length == 0) {
            p.sendMessage(lang.Prefix + lang.InvalidPlayer);
        } else {

            BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[0]));
            if (target == null || !target.isOnline()) {
                p.sendMessage(lang.Prefix + lang.InvalidPlayer);
                return false;
            }
            if (!target.getUniqueId().equals(p.getUniqueId())) {
                Effect.particle(Settings.Particle.teleportStart(target.getLocation()));

                target.teleport(p.getLocation());

                Effect.particle(Settings.Particle.teleportEnd(target.getLocation()));
                Effect.sound(p, Settings.Sound.teleported());
                Effect.sound(target, Settings.Sound.teleported());

                StringHandler message = new StringHandler(lang.TeleportedToYou).setPrefix(lang.Prefix);
                message.replaceAll("{player}", target.getName());
                message.send(p);
            } else {
                p.sendMessage(lang.Prefix + lang.CantTargetYourSelf);
            }

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
