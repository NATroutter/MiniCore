package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.natlibs.handlers.LangHandler.language.LangManager;
import net.natroutter.natlibs.objects.BaseItem;

import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Rename extends Command {

	private LangManager lang;
	private Effects effects;

	public Rename(Handler handler) {
		super("Rename");
		lang = handler.getLang();
		effects = handler.getEffects();
	}
	
	@Override
	public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
		if (!(sender instanceof Player p)) {
			lang.send(sender, Translations.Prefix, Translations.OnlyIngame);
			return false;
		}

		if (!sender.hasPermission("minicore.rename")) {
			lang.send(sender, Translations.Prefix, Translations.NoPerm);
			return false;
		}

		if (args.length == 0) {
			lang.send(p, Translations.Prefix, Translations.InvalidArgs);
			
		} else {
			StringHandler name = new StringHandler(args, ' ');
			if (p.hasPermission("minicore.rename.color")) {
				name.replaceColors();
			}
			BaseItem item = BaseItem.from(p.getInventory().getItemInMainHand());

			if (!item.getType().equals(Material.AIR)) {
				item.setDisplayName(name.build());
				p.updateInventory();
				lang.send(p, Translations.Prefix, Translations.ItemRenamed);
				effects.sound(p, Sounds.Modified);

			} else {
				lang.send(p, Translations.Prefix, Translations.InvalidItem);
			}
		}
		
		return false;
	}
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return null;
	}
}
