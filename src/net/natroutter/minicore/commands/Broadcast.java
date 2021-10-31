package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Config;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Broadcast extends Command {

    public Broadcast() {
        super("");
    }

    private final Lang lang = MiniCore.getLang();
    private final Config config = MiniCore.getConf();

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("minicore.broadcast")) {
            sender.sendMessage(lang.Prefix + lang.NoPerm);
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(lang.Prefix + lang.InvalidBroadcastMessage);

        } else {
            StringHandler message = new StringHandler(args, ' ');
            if (sender.hasPermission("minicore.broadcast.colors")) {
                message.replaceColors();
            }

            StringHandler broadcast = new StringHandler(config.BroadcastFormat);
            broadcast.replaceAll("{message}", message.build());
            for (Player onlineP : Bukkit.getOnlinePlayers()) {
                broadcast.send(onlineP);
                Effect.sound(onlineP, Settings.Sound.broadcast());
            }
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }
}

