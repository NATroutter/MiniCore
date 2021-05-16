package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.objects.BasePlayer;
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

public class Cleanitem extends Command {

    public Cleanitem() {
        super("");
        this.setPermission("minicore.cleanitem");
        this.setPermissionMessage(lang.NoPerm);
        this.setAliases(Collections.singletonList("citem"));
    }

    private final Lang lang = MiniCore.getLang();

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.OnlyIngame);
            return false;
        }
        BasePlayer p = BasePlayer.from(sender);

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
}