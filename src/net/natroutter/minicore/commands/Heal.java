package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.*;

import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Heal extends Command {

    public Heal() {
        super("");
    }

    private final Lang lang = MiniCore.getLang();
    private final Config config = MiniCore.getConf();

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(lang.InvalidArgs);
                return false;
            }
            Player p = (Player)sender;

            if (p.hasPermission("minicore.heal")) {
                p.setHealth(20);
                p.sendMessage(lang.Prefix + lang.Healed);
                Effect.particle(Settings.Particle.heal(p.getLocation()));
                Effect.sound(p, Settings.Sound.heal());
            } else {
                p.sendMessage(lang.Prefix + lang.NoPerm);
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("minicore.heal.other")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null || !target.isOnline()) {
                    sender.sendMessage(lang.Prefix + lang.InvalidPlayer);
                    return false;
                }

                StringHandler message = new StringHandler(lang.HealedOther).setPrefix(lang.Prefix);
                message.replaceAll("{player}", target.getName());

                if (sender instanceof Player) {
                    Player p = (Player)sender;
                    if (!target.getUniqueId().equals(p.getUniqueId())) {
                        target.setHealth(20);
                        message.send(p);

                        Effect.sound(target, Settings.Sound.heal());
                    } else {
                        p.setHealth(20);
                        p.sendMessage(lang.Prefix + lang.Healed);
                    }
                    Effect.sound(p, Settings.Sound.heal());
                } else {
                    target.setHealth(20);
                    sender.sendMessage(message.build());
                    Effect.sound(target, Settings.Sound.heal());
                }
                Effect.particle(Settings.Particle.heal(target.getLocation()));

            } else {
                sender.sendMessage(lang.Prefix + lang.NoPerm);
            }
        } else {
            sender.sendMessage(lang.Prefix + lang.ToomanyArgs);
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