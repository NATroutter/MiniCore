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
import java.util.List;

public class Cleanchat extends Command {

    public Cleanchat() {
        super("");
        this.setPermission("minicore.cleanchat");
        this.setPermissionMessage(lang.NoPerm);
        this.setAliases(Collections.singletonList("cc"));
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
            for (int i = 0; i < 255; i++) {
                Bukkit.broadcastMessage(" ");
            }
            StringHandler message = new StringHandler(lang.ChatCleaned).setPrefix(lang.Prefix);
            message.replaceAll("{player}", p.getName());
            for (BasePlayer onlineP : BasePlayer.getOnlinePlayers()) {
                message.send(onlineP);
                Effect.sound(p, Settings.Sound.modified());
            }
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
