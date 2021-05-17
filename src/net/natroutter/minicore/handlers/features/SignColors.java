package net.natroutter.minicore.handlers.features;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignColors implements Listener {


    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (e.getPlayer().hasPermission("minicore.signcolor")) {
            for (int i = 0; i < e.getLines().length; i++) {
                String line = e.getLine(i);
                e.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
            }
        }
    }


}
