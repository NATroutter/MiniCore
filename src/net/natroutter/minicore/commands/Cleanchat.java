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

import java.util.Collections;
import java.util.List;

public class Cleanchat extends Command {

    public Cleanchat() {
        super("");
        this.setAliases(Collections.singletonList("cc"));
    }

    private final Lang lang = MiniCore.getLang();
    private final Config config = MiniCore.getConf();

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("minicore.cleanchat")) {
            sender.sendMessage(lang.Prefix + lang.NoPerm);
            return false;
        }

        if (args.length == 0) {
            for (int i = 0; i < 255; i++) {
                Bukkit.broadcastMessage(" ");
            }
            StringHandler message = new StringHandler(lang.ChatCleaned).setPrefix(lang.Prefix);

            if (sender instanceof Player) {
                message.replaceAll("{player}", ((Player) sender).getName());
            } else {
                message.replaceAll("{player}", lang.ConsoleName);
            }

            for (Player onlineP : Bukkit.getOnlinePlayers()) {
                message.send(onlineP);
                Effect.sound(onlineP, Settings.Sound.modified());
            }
        } else {
            sender.sendMessage(lang.Prefix + lang.ToomanyArgs);
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }
}
