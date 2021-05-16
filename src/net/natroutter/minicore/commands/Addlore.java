package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.natlibs.objects.BaseItem;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Addlore extends Command {

    private final Lang lang = MiniCore.getLang();

    public Addlore() {
        super("");
        this.setPermission("minicore.addlore");
        this.setPermissionMessage(lang.Prefix + lang.NoPerm);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.OnlyIngame);
            return false;
        }

        BasePlayer p = BasePlayer.from(sender);

        if (args.length == 0) {
            p.sendMessage(lang.Prefix + lang.InvalidArgs);

        } else {
            StringHandler lore = new StringHandler(args, ' ');
            if (p.hasPermission("minicore.addlore.color")) {
                lore.replaceColors();
            }
            BaseItem item = BaseItem.from(p.getInventory().getItemInMainHand());

            if (!item.getType().equals(Material.AIR)) {
                item.addLore(lore.split('|'));
                p.updateInventory();
                p.sendMessage(lang.Prefix + lang.LoreAdded);
                Effect.sound(p, Settings.Sound.modified());

            } else {
                p.sendMessage(lang.Prefix + lang.InvalidItem);
            }
        }
        return false;
    }
}
