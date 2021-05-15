package net.natroutter.minicore.handlers;

import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.chat.Chat;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Config;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormater implements Listener {



    Hooks hooks = MiniCore.getHooks();
    Config cfg = MiniCore.getConf();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        BasePlayer p = BasePlayer.from(e.getPlayer());

        StringHandler message = new StringHandler(e.getMessage());
        message.replaceAll("%", "%%");

        if (p.hasPermission("minicore.chatcolor")) {
            message.replaceAll("&", "§");
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
        format.replaceAll("{displayname}", p.getDisplayName());
        format.replaceAll("{message}", message);
        format.replaceAll("{suffix}", suffix);

        if (hooks.PlaceHolderApi.isHooked()) {
            format = StringHandler.from(format, PlaceholderAPI.setPlaceholders(p, format.build()));
        }

        e.setFormat(format.build());
    }
}
