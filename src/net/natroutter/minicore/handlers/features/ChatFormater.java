package net.natroutter.minicore.handlers.features;

import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.chat.Chat;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Hooks;
import net.natroutter.minicore.utilities.Config;

import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormater implements Listener {



    Hooks hooks = MiniCore.getHooks();
    Config cfg = MiniCore.getConf();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        StringHandler message = new StringHandler(e.getMessage());
        message.replaceAll("%", "%%");

        if (p.hasPermission("minicore.chatcolor")) {
            message.replaceAll("&", "§");
        } else {
            message.stripColors();
        }

        String prefix = "";
        String suffix = "";
        if (hooks.vault.isHooked()) {
            Chat chat = hooks.vault.getChat();
            prefix = chat.getPlayerPrefix(p).replaceAll("&", "§");
            suffix = chat.getPlayerSuffix(p).replaceAll("&", "§");
        }

        StringHandler format = new StringHandler(cfg.ChatFormat);
        format.replaceAll("{prefix}", prefix);
        format.replaceAll("{name}", p.getName());
        format.replaceAll("{displayname}", p.getDisplayName());
        format.replaceAll("{message}", message);
        format.replaceAll("{suffix}", suffix);

        if (hooks.PlaceHolderApi.isHooked()) {
            format = StringHandler.from(format, PlaceholderAPI.setPlaceholders(p, format.build()));
        }

        e.setFormat(format.build());
    }
}
