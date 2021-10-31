package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.objects.ParticleSettings;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class Cleanitem extends Command {

    public Cleanitem() {
        super("");
        this.setAliases(Collections.singletonList("citem"));
    }

    private final Lang lang = MiniCore.getLang();

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("minicore.cleanitem")) {
            sender.sendMessage(lang.Prefix + lang.NoPerm);
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.OnlyIngame);
            return false;
        }
        Player p = (Player)sender;

        if (args.length == 0) {

            ItemStack item = p.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() || meta.hasLore()) {
                if (meta.hasDisplayName()) {
                    meta.setDisplayName(null);
                }
                if (meta.hasLore()) {
                    meta.setLore(null);
                }
                item.setItemMeta(meta);
                Effect.sound(p, Settings.Sound.modified());
                p.sendMessage(lang.Prefix + lang.ItemCleaned);
            } else {
                p.sendMessage(lang.Prefix + lang.ItemAlreadyClean);
            }
        } else {
            p.sendMessage(lang.Prefix + lang.ToomanyArgs);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }
}