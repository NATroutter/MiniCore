package net.natroutter.minicore.handlers.features;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Database.handlers.PlayerDataHandler;
import net.natroutter.minicore.handlers.Database.tables.PlayerData;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import javax.xml.crypto.Data;

public class InfoHandler implements Listener {

    private static final Utilities utils = MiniCore.getUtilities();
    public static final Character sep = '/';

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        updatePlayer(e.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        updatePlayer(e.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            updatePlayer(p);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            updatePlayer(p);
        }
    }

    @EventHandler
    public void onLeave(PlayerExpChangeEvent e) {
        updatePlayer(e.getPlayer());
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        updatePlayer(e.getPlayer());
    }

    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent e) {
        if (e.getEntered() instanceof Player) {
            Player p = (Player) e.getEntered();
            updatePlayer(p);
        }
    }

    @EventHandler
    public void onGamemodeChange(VehicleExitEvent e) {
        if (e.getExited() instanceof Player) {
            Player p = (Player) e.getExited();
            updatePlayer(p);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.hasBlock()) {return;}
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {return;}
        if (!e.getClickedBlock().getType().name().endsWith("_BED")) {return;}
        updatePlayer(e.getPlayer());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        updatePlayer(e.getPlayer());
    }

    public static PlayerData updatePlayer(OfflinePlayer p) {
        PlayerData data = PlayerDataHandler.queryForID(p.getUniqueId());
        if (data != null) {
            if (p.isOnline()) {
                Player onlineP = p.getPlayer();
                if (onlineP != null) {
                    if (onlineP.getAddress() != null) {
                        data.setIp(onlineP.getAddress().getAddress().getHostAddress());
                    }
                    data.setDisplayname(onlineP.getDisplayName());
                    data.setLastloc(utils.serializeLocation(onlineP.getLocation(), sep));
                    data.setLocale(onlineP.getLocale());
                    data.setHealth(onlineP.getHealth());
                    data.setMaxhealth(onlineP.getMaxHealth());
                    data.setFood(onlineP.getFoodLevel());
                    data.setLevel(onlineP.getLevel());
                    data.setGamemode(onlineP.getGameMode().toString());
                    data.setFlyspeed(onlineP.getFlySpeed());
                    data.setWalkspeed(onlineP.getWalkSpeed());
                    data.setWorld(onlineP.getWorld().getName());
                    data.setFlying(onlineP.isFlying());
                    data.setAllowedfly(onlineP.getAllowFlight());
                    data.setInvehicle(onlineP.isInsideVehicle());
                    data.setLastplayed(System.currentTimeMillis());
                }
            }
            data.setName(p.getName());
            data.setOnline(p.isOnline());
            data.setFirstplayed(p.getFirstPlayed());
            if (p.getBedSpawnLocation() != null) {
                data.setBedlocation(utils.serializeLocation(p.getBedSpawnLocation(), sep));
            } else {
                data.setBedlocation("Unknown");
            }
            PlayerDataHandler.updateForID(data);
        }
        return data;
    }

}
