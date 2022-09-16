package net.natroutter.minicore.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.chat.Chat;
import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.handlers.Hooks;

import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.natlibs.handlers.langHandler.language.LangManager;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class List extends Command {

    private LangManager lang;
    private Effects effects;
    private Hooks hooks;

    public List(Handler handler) {
        super("List");
        lang = handler.getLang();
        effects = handler.getEffects();
        hooks = handler.getHooks();
    }

    @Override
    public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
        if (!sender.hasPermission("minicore.list")) {
            lang.send(sender, Translations.Prefix, Translations.NoPerm);
            return false;
        }

        if (args.length == 0) {

            ArrayList<String> playerEntries = new ArrayList<>();
            for (Player onlineP : Bukkit.getOnlinePlayers()) {
                StringHandler entry = new StringHandler(lang.get(Translations.ListFormat_Entry));

                String prefix = "";
                String suffix = "";
                if (hooks.vault.isHooked()) {
                    Chat chat = hooks.vault.getChat();
                    prefix = chat.getPlayerPrefix(onlineP).replaceAll("&", "§");
                    suffix = chat.getPlayerSuffix(onlineP).replaceAll("&", "§");
                }
                entry.replaceAll("%prefix%", prefix);
                entry.replaceAll("%name%", onlineP.getName());
                entry.replaceAll("%displayname%", onlineP.getDisplayName());
                entry.replaceAll("%suffix%", suffix);

                if (hooks.PlaceHolderApi.isHooked()) {
                    entry = StringHandler.from(entry, PlaceholderAPI.setPlaceholders(onlineP, entry.build()));
                }
                playerEntries.add(entry.build());
            }

            StringHandler entries = new StringHandler(playerEntries, lang.get(Translations.ListFormat_Separator));
            for (String line : lang.getList(Translations.ListFormat_Format)) {
                StringHandler msgLine = new StringHandler(line);
                msgLine.replaceAll("%entries%", entries);
                msgLine.replaceAll("%online%", Bukkit.getOnlinePlayers().size());
                msgLine.replaceAll("%max%", Bukkit.getServer().getMaxPlayers());
                sender.sendMessage(msgLine.build());
            }
            if (sender instanceof Player p) {
                effects.sound(p, Sounds.Modified);
            }
        } else {
            lang.send(sender, Translations.Prefix, Translations.ToomanyArgs);
        }
        return false;
    }

    @Override
    public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }
}