package net.natroutter.minicore.handlers.features;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Database.handlers.PlayerDataHandler;
import net.natroutter.minicore.handlers.Database.tables.PlayerData;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodHandler implements Listener {

    PlayerDataHandler pdh = MiniCore.getDataHandler();

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            PlayerData data = pdh.get(p.getUniqueId());
            if (data != null) {
                if (data.getGod()) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
