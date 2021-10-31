package net.natroutter.minicore.handlers.features;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Database.handlers.PlayerDataHandler;
import net.natroutter.minicore.handlers.Database.tables.PlayerData;
import net.natroutter.minicore.utilities.Utils;
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
    private static final PlayerDataHandler pdh = MiniCore.getDataHandler();


    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (!Utils.isValidPlayer(e.getPlayer())) {return;}
        updatePlayer(e.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (!Utils.isValidPlayer(p)) {return;}
            updatePlayer(p);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (!Utils.isValidPlayer(p)) {return;}
            updatePlayer(p);
        }
    }

    @EventHandler
    public void onLeave(PlayerExpChangeEvent e) {
        if (!Utils.isValidPlayer(e.getPlayer())) {return;}
        updatePlayer(e.getPlayer());
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        if (!Utils.isValidPlayer(e.getPlayer())) {return;}
        updatePlayer(e.getPlayer());
    }

    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent e) {
        if (e.getEntered() instanceof Player) {
            Player p = (Player) e.getEntered();
            if (!Utils.isValidPlayer(p)) {return;}
            updatePlayer(p);
        }
    }

    @EventHandler
    public void onGamemodeChange(VehicleExitEvent e) {
        if (e.getExited() instanceof Player) {
            Player p = (Player) e.getExited();
            if (!Utils.isValidPlayer(p)) {return;}
            updatePlayer(p);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.hasBlock()) {return;}
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {return;}
        if (!e.getClickedBlock().getType().name().endsWith("_BED")) {return;}
        if (!Utils.isValidPlayer(e.getPlayer())) {return;}
        updatePlayer(e.getPlayer());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (!Utils.isValidPlayer(e.getPlayer())) {return;}
        updatePlayer(e.getPlayer());
    }

    public static PlayerData updatePlayer(Player p) {
        PlayerData data = pdh.get(p.getUniqueId());
        if (data != null) {

            try {
                if (p.getAddress() != null && p.getAddress().getAddress().getHostAddress() != null) {
                    data.setIp(p.getAddress().getAddress().getHostAddress());
                }
            }catch (Exception ignored) {}

            data.setDisplayname(p.getDisplayName());
            data.setLastloc(utils.serializeLocation(p.getLocation(), sep));
            data.setLocale(p.getLocale());
            data.setHealth(p.getHealth());
            data.setMaxhealth(p.getMaxHealth());
            data.setFood(p.getFoodLevel());
            data.setLevel(p.getLevel());
            data.setGamemode(p.getGameMode().name());
            data.setFlyspeed(p.getFlySpeed());
            data.setWalkspeed(p.getWalkSpeed());
            data.setWorld(p.getWorld().getName());
            data.setFlying(p.isFlying());
            data.setAllowedfly(p.getAllowFlight());
            data.setInvehicle(p.isInsideVehicle());
            data.setLastplayed(System.currentTimeMillis());


            data.setName(p.getName());
            data.setOnline(p.isOnline());
            data.setFirstplayed(p.getFirstPlayed());
            if (p.getBedSpawnLocation() != null) {
                data.setBedlocation(utils.serializeLocation(p.getBedSpawnLocation(), sep));
            } else {
                data.setBedlocation("Unknown");
            }
            pdh.set(data);
        }
        return data;
    }

}
