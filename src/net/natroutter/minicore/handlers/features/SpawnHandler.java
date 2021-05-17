package net.natroutter.minicore.handlers.features;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Config;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.objects.BasePlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnHandler implements Listener {

    private Config config = MiniCore.getConf();
    private YamlDatabase database = MiniCore.getYamlDatabase();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        BasePlayer p = BasePlayer.from(e.getPlayer());
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