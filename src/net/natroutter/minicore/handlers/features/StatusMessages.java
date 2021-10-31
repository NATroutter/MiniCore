package net.natroutter.minicore.handlers.features;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Database.handlers.PlayerDataHandler;
import net.natroutter.minicore.handlers.Database.tables.PlayerData;
import net.natroutter.minicore.utilities.Config;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;

import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StatusMessages implements Listener {

    private Config config = MiniCore.getConf();
    private Lang lang = MiniCore.getLang();
    private YamlDatabase database = MiniCore.getYamlDatabase();
    private PlayerDataHandler pdh = MiniCore.getDataHandler();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (config.DisableFlyOnJoin) {
            p.setAllowFlight(false);
            p.setFlying(false);
        } else {
            if (pdh.get(p.getUniqueId()) != null) {
                if (pdh.get(p.getUniqueId()).getAllowedfly()) {
                    p.setAllowFlight(true);
                    p.setFlying(true);
                }
            }
        }

        //Force teleport palyers to psawn
        if (config.ForceSpawnOnJoin) {
            Location spawn = database.getLocation("General", "SpawnLoc");
            if (spawn != null) {
                p.teleport(spawn);
            } else {
                p.teleport(p.getWorld().getSpawnLocation());
            }
        }

        //Disable god mode
        if (config.DisableGodOnJoin) {
            PlayerData data = pdh.get(p.getUniqueId());
            if (data != null) {
                data.setGod(false);
                pdh.set(data);
            }
        }


        //join messages
        if (config.DisableJoinMessage) {
            e.setJoinMessage(null);
        } else {
            if (config.WelcomeMessages) {

                if (!p.hasPlayedBefore()) {
                    StringHandler message = new StringHandler(lang.FirstTimeWelcome);
                    message.setPrefix(lang.Prefix);
                    message.replaceAll("{player}", p.getName());
                    Bukkit.broadcastMessage(message.build());
                }

                if (config.ShowJoinMessageIfNewPlayer) {
                    if (config.UseCustomJoinMessage) {
                        StringHandler message = new StringHandler(lang.Joined);
                        message.setPrefix(lang.Prefix);
                        message.replaceAll("{player}", p.getName());
                        e.setJoinMessage(message.build());
                    }
                } else {
                    e.setJoinMessage(null);
                }
            } else {
                if (config.UseCustomJoinMessage) {
                    StringHandler message = new StringHandler(lang.Joined);
                    message.setPrefix(lang.Prefix);
                    message.replaceAll("{player}", p.getName());
                    e.setJoinMessage(message.build());
                }
            }
        }

        if (!Utils.isValidPlayer(p)) {return;}
        InfoHandler.updatePlayer(p);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (config.DisableQuitMessage) {
            e.setQuitMessage(null);
        } else {
            if (config.UseCustomJoinMessage) {
                StringHandler message = new StringHandler(lang.Quit);
                message.setPrefix(lang.Prefix);
                message.replaceAll("{player}", p.getName());
                e.setQuitMessage(message.build());
            }
        }
    }


}
