package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.*;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class Cleaninventory extends Command {

    public Cleaninventory() {
        super("");
        this.setAliases(Collections.singletonList("ci"));
    }

    private final Lang lang = MiniCore.getLang();
    private final Config config = MiniCore.getConf();

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("minicore.Cleaninventory")) {
            sender.sendMessage(lang.Prefix + lang.NoPerm);
            return false;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(lang.InvalidArgs);
                return false;
            }
            Player p = (Player)sender;

            if (p.hasPermission("minicore.cleaninventory")) {
                p.getInventory().clear();
                p.sendMessage(lang.Prefix + lang.inventoryCleaned);
                Effect.sound(p, Settings.Sound.modified());
            } else {
                p.sendMessage(lang.Prefix + lang.NoPerm);
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("minicore.cleaninventory.other")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null || !target.isOnline()) {
                    sender.sendMessage(lang.Prefix + lang.InvalidPlayer);
                    return false;
                }

                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (!target.getUniqueId().equals(p.getUniqueId())) {
                        target.getInventory().clear();

                        StringHandler message = new StringHandler(lang.inventoryCleanedOther).setPrefix(lang.Prefix);
                        message.replaceAll("{player}", target.getName());
                        message.send(p);
                        Effect.sound(target, Settings.Sound.modified());
                        Effect.sound(p, Settings.Sound.modified());
                    } else {
                        p.getInventory().clear();
                        p.sendMessage(lang.Prefix + lang.inventoryCleaned);
                        Effect.sound(p, Settings.Sound.modified());
                    }
                } else {
                    target.getInventory().clear();

                    StringHandler message = new StringHandler(lang.inventoryCleanedOther).setPrefix(lang.Prefix);
                    message.replaceAll("{player}", target.getName());
                    sender.sendMessage(message.build());
                    Effect.sound(target, Settings.Sound.modified());
                }

            } else {
                sender.sendMessage(lang.Prefix+lang.NoPerm);
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
