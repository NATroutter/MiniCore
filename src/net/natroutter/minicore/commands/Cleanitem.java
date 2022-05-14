package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.natlibs.handlers.LangHandler.language.LangManager;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class Cleanitem extends Command {

    private LangManager lang;
    private Effects effects;

    public Cleanitem(Handler handler) {
        super("Cleanitem");
        this.setAliases(Collections.singletonList("citem"));
        lang = handler.getLang();
        effects = handler.getEffects();
    }

    @Override
    public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
        if (!(sender instanceof Player p)) {
            lang.send(sender, Translations.Prefix, Translations.OnlyIngame);
            return false;
        }

        if (!sender.hasPermission("minicore.cleanitem")) {
            lang.send(sender, Translations.Prefix, Translations.NoPerm);
            return false;
        }

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
                effects.sound(p, Sounds.Modified);
                lang.send(p, Translations.Prefix, Translations.ItemCleaned);
            } else {
                lang.send(p, Translations.Prefix, Translations.ItemAlreadyClean);
            }
        } else {
            lang.send(p, Translations.Prefix, Translations.ToomanyArgs);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }
}