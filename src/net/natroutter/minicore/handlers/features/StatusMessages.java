package net.natroutter.minicore.handlers.features;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.natlibs.handlers.database.YamlDatabase;
import net.natroutter.natlibs.handlers.langHandler.language.LangManager;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class StatusMessages implements Listener {

    private Config config;
    private LangManager lang;
    private YamlDatabase database;

    public StatusMessages(Handler handler) {
        config = handler.getConfig();
        lang = handler.getLang();
        database = handler.getYamlDatabase();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (config.DisableFlyOnJoin) {
            p.setAllowFlight(false);
            p.setFlying(false);
        }

        //Force teleport palyers to psawn
        if (config.ForceSpawnOnJoin) {
            Location spawn = database.getLocation("General", "SpawnLoc");
            p.teleport(Objects.requireNonNullElseGet(spawn, () -> p.getWorld().getSpawnLocation()));
        }

        //Disable god mode
        if (config.DisableGodOnJoin) {
            database.save(p, "God", false);
        }


        //join messages
        if (config.DisableJoinMessage) {
            e.setJoinMessage(null);
        } else {
            if (config.WelcomeMessages) {

                if (!p.hasPlayedBefore()) {
                    StringHandler message = new StringHandler(lang.get(Translations.FirstTimeWelcome));
                    message.setPrefix(lang.get(Translations.Prefix));
                    message.replaceAll("%player%", p.getName());
                    Bukkit.broadcastMessage(message.build());
                }

                if (config.ShowJoinMessageIfNewPlayer) {
                    if (config.UseCustomJoinMessage) {
                        StringHandler message = new StringHandler(lang.get(Translations.Joined));
                        message.setPrefix(lang.get(Translations.Prefix));
                        message.replaceAll("%player%", p.getName());
                        e.setJoinMessage(message.build());
                    }
                } else {
                    e.setJoinMessage(null);
                }
            } else {
                if (config.UseCustomJoinMessage) {
                    StringHandler message = new StringHandler(lang.get(Translations.Joined));
                    message.setPrefix(lang.get(Translations.Prefix));
                    message.replaceAll("%player%", p.getName());
                    e.setJoinMessage(message.build());
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (config.DisableQuitMessage) {
            e.setQuitMessage(null);
        } else {
            if (config.UseCustomJoinMessage) {
                StringHandler message = new StringHandler(lang.get(Translations.Quit));
                message.setPrefix(lang.get(Translations.Prefix));
                message.replaceAll("%player%", p.getName());
                e.setQuitMessage(message.build());
            }
        }
    }


}