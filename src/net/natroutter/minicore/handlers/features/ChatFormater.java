package net.natroutter.minicore.handlers.features;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Hooks;
import net.natroutter.minicore.files.Config;

import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class ChatFormater implements Listener,ChatRenderer {

    private Hooks hooks;
    private Config config;
    public ChatFormater(Handler handler) {
        hooks = handler.getHooks();
        config = handler.getConfig();
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        e.renderer(this);
    }

    @Override
    public @NotNull Component render(@NotNull Player p, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        String displayName = PlainTextComponentSerializer.plainText().serialize(p.displayName());
        String messageString = PlainTextComponentSerializer.plainText().serialize(message);

        if (p.hasPermission("minicore.chatcolor")) {
            messageString = ChatColor.translateAlternateColorCodes('&', messageString);
        }

        messageString = messageString.replace("\\", "\\\\");
        messageString = messageString.replace("$", "\\$");

        String prefix = "";
        String suffix = "";
        if (hooks.vault.isHooked()) {
            Chat chat = hooks.vault.getChat();
            prefix = chat.getPlayerPrefix(p).replaceAll("&", "§");
            suffix = chat.getPlayerSuffix(p).replaceAll("&", "§");
        }

        StringHandler format = new StringHandler(config.ChatFormat);
        format.replaceAll("%prefix%", prefix);
        format.replaceAll("%name%", p.getName());
        format.replaceAll("%displayname%", displayName);
        format.replaceAll("%message%", messageString);
        format.replaceAll("%suffix%", suffix);

        if (hooks.PlaceHolderApi.isHooked()) {
            format = StringHandler.from(format, PlaceholderAPI.setPlaceholders(p, format.build()));
        }

        return Component.text(format.build());
    }
}
