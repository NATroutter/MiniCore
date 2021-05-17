package net.natroutter.minicore.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.chat.Chat;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Hooks;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class List extends Command {

    public List() {
        super("");
        this.setPermission("minicore.list");
        this.setPermissionMessage(lang.NoPerm);
    }

    private final Lang lang = MiniCore.getLang();
    Hooks hooks = MiniCore.getHooks();

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {

            ArrayList<String> playerEntries = new ArrayList<>();
            for (Player onlineP : Bukkit.getOnlinePlayers()) {
                StringHandler entry = new StringHandler(lang.ListFormat.Entry);

                String prefix = "";
                String suffix = "";
                if (hooks.vault.isHooked()) {
                    Chat chat = hooks.vault.getChat();
                    prefix = chat.getPlayerPrefix(onlineP).replaceAll("&", "§");
                    suffix = chat.getPlayerSuffix(onlineP).replaceAll("&", "§");
                }
                entry.replaceAll("{prefix}", prefix);
                entry.replaceAll("{name}", onlineP.getName());
                entry.replaceAll("{displayname}", onlineP.getDisplayName());
                entry.replaceAll("{suffix}", suffix);

                if (hooks.PlaceHolderApi.isHooked()) {
                    entry = StringHandler.from(entry, PlaceholderAPI.setPlaceholders(onlineP, entry.build()));
                }
                playerEntries.add(entry.build());
            }

            StringHandler entries = new StringHandler(playerEntries, lang.ListFormat.Separator);
            for (String line : lang.ListFormat.Format) {
                StringHandler msgLine = new StringHandler(line);
                msgLine.replaceAll("{entries}", entries);
                msgLine.replaceAll("{online}", Bukkit.getOnlinePlayers().size());
                msgLine.replaceAll("{max}", Bukkit.getServer().getMaxPlayers());
                sender.sendMessage(msgLine.build());
            }
            if (sender instanceof Player) {
                BasePlayer p = BasePlayer.from(sender);
                Effect.sound(p, Settings.Sound.modified());
            }
        } else {
            sender.sendMessage(lang.Prefix + lang.ToomanyArgs);
        }
        return false;
    }

    @Override
    public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }
}