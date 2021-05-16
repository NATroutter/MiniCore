package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Config;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.natlibs.objects.BaseItem;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class Broadcast extends Command {

    public Broadcast() {
        super("");
        this.setPermission("minicore.broadcast");
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
            p.sendMessage(lang.Prefix + lang.InvalidBroadcastMessage);

        } else {
            StringHandler message = new StringHandler(args, ' ');
            if (p.hasPermission("minicore.broadcast.colors")) {
                message.replaceColors();
            }

            StringHandler broadcast = new StringHandler(config.BroadcastFormat);
            broadcast.replaceAll("{message}", message.build());
            for (BasePlayer onlineP : BasePlayer.getOnlinePlayers()) {
                broadcast.send(onlineP);
                Effect.sound(p, Settings.Sound.broadcast());
            }
        }
        return false;
    }
}

