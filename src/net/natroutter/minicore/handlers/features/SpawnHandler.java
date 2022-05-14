package net.natroutter.minicore.handlers.features;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Config;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnHandler implements Listener {

    private Config config;
    private YamlDatabase database;

    public SpawnHandler(Handler handler) {
        config = handler.getConfig();
        database = handler.getYamlDatabase();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (config.AllowBedSpawn) {
            if (p.getBedSpawnLocation() != null) {
                e.setRespawnLocation(p.getBedSpawnLocation());
                return;
            }
        }
        Location spawn = database.getLocation("General", "SpawnLoc");
        if (spawn != null) {
            e.setRespawnLocation(spawn);
        } else {
            e.setRespawnLocation(p.getWorld().getSpawnLocation());
        }

    }




}
