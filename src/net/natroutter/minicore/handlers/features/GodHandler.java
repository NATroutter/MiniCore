package net.natroutter.minicore.handlers.features;

import net.natroutter.minicore.handlers.Database.handlers.PlayerDataHandler;
import net.natroutter.minicore.handlers.Database.tables.PlayerData;
import net.natroutter.natlibs.objects.BasePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodHandler implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            BasePlayer p = BasePlayer.from(e.getEntity());
            PlayerData data = PlayerDataHandler.queryForID(p.getUniqueId());
            if (data != null) {
                if (data.getGod()) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
