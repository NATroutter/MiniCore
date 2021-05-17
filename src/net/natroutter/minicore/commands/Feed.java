package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.*;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class Feed extends Command {

    public Feed() {
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
            BasePlayer p = BasePlayer.from(sender);

            if (p.hasPermission("minicore.feed")) {
                p.setFoodLevel(20);
                p.sendMessage(lang.Prefix + lang.Fed);
                Effect.sound(p, Settings.Sound.eat());
                Effect.particle(Settings.Particle.feed(p.getLocation()));
            } else {
                p.sendMessage(lang.Prefix + lang.NoPerm);
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("minicore.feed.other")) {
                BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[0]));
                if (target == null || !target.isOnline()) {
                    sender.sendMessage(lang.Prefix + lang.InvalidPlayer);
                    return false;
                }
                if (sender instanceof Player) {
                    BasePlayer p = BasePlayer.from(sender);
                    if (!target.getUniqueId().equals(p.getUniqueId())) {
                        target.setFoodLevel(20);

                        StringHandler message = new StringHandler(lang.FedOther).setPrefix(lang.Prefix);
                        message.replaceAll("{player}", target.getName());
                        message.send(p);

                        Effect.sound(p, Settings.Sound.eat());
                        Effect.sound(target, Settings.Sound.eat());

                        Effect.particle(Settings.Particle.feed(target.getLocation()));
                    } else {
                        p.setFoodLevel(20);
                        p.sendMessage(lang.Prefix + lang.Fed);
                        Effect.sound(p, Settings.Sound.eat());
                        Effect.particle(Settings.Particle.feed(p.getLocation()));
                    }
                } else {
                    target.setFoodLevel(20);

                    StringHandler message = new StringHandler(lang.FedOther).setPrefix(lang.Prefix);
                    message.replaceAll("{player}", target.getName());
                    sender.sendMessage(message.build());
                    Effect.sound(target, Settings.Sound.eat());

                    Effect.particle(Settings.Particle.feed(target.getLocation()));
                }
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
