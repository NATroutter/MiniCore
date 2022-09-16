package net.natroutter.minicore.handlers.features;

import net.natroutter.minicore.Handler;

import net.natroutter.natlibs.handlers.database.YamlDatabase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodHandler implements Listener {

    private YamlDatabase database;
    public GodHandler(Handler handler) {
        database = handler.getYamlDatabase();
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p) {
            if (database.getBoolean(p, "God")) {
                e.setCancelled(true);
            }
        }
    }

}
