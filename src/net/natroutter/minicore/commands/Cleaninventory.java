package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Config;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class Cleaninventory extends Command {

    public Cleaninventory() {
        super("");
        this.setPermission("minicore.cleaninventory");
        this.setPermissionMessage(lang.NoPerm);
        this.setAliases(Collections.singletonList("ci"));
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

            p.getInventory().clear();
            p.sendMessage(lang.Prefix + lang.inventoryCleaned);
            Effect.sound(p, Settings.Sound.modified());

        } else if (args.length == 1) {

            BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[0]));
            if (target == null || !target.isOnline()) {
                p.sendMessage(lang.Prefix + lang.InvalidPlayer);
                return false;
            }
            target.getInventory().clear();

            StringHandler message = new StringHandler(lang.inventoryCleanedOther).setPrefix(lang.Prefix);
            message.replaceAll("{player}", target.getName());
            message.send(p);
            Effect.sound(target, Settings.Sound.modified());
            Effect.sound(p, Settings.Sound.modified());

        } else {
            p.sendMessage(lang.Prefix + lang.ToomanyArgs);
        }

        return false;
    }
}
