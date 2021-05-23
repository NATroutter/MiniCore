package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.*;

import org.bukkit.Bukkit;
import org.bukkit.World;
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
        if (args.length == 0) {

            if (sender instanceof Player) {
                Player p = (Player)sender;
                p.getWorld().setTime(14000);
                p.sendMessage(lang.Prefix + lang.TimeSetToNight);
                Effect.sound(p, Settings.Sound.modified());
            } else {
                sender.sendMessage(lang.Prefix + lang.WorldNeeded);
            }
        } else if (args.length == 1) {
            World world = Bukkit.getWorld(args[0]);
            if (world == null) {
                sender.sendMessage(lang.Prefix + lang.InvalidWorld);
                return false;
            }
            world.setTime(14000);
            sender.sendMessage(lang.Prefix + lang.TimeSetToNight);

        } else {
            sender.sendMessage(lang.Prefix + lang.ToomanyArgs);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return Utils.worldNameList();
        }
        return null;
    }
}
