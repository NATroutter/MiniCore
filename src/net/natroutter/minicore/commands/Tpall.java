package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.*;

import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.List;

public class Tpall extends Command {

    public Tpall() {
        super("");
        this.setPermission("minicore.tpall");
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
            for (Player onlineP : Bukkit.getOnlinePlayers()) {
                onlineP.teleport(p);
                Effect.sound(onlineP, Settings.Sound.teleported());
            }
            p.sendMessage(lang.Prefix + lang.AllPlayersTeleported);
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
