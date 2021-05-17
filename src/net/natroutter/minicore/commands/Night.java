package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.*;
import net.natroutter.natlibs.objects.BasePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Night extends Command {

    public Night() {
        super("");
        this.setPermission("minicore.night");
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

            p.getWorld().setTime(14000);
            p.sendMessage(lang.Prefix + lang.TimeSetToNight);
            Effect.sound(p, Settings.Sound.modified());

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
