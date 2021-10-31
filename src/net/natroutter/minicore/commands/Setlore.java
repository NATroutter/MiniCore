package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.objects.BaseItem;

import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Setlore extends Command {

	private final Lang lang = MiniCore.getLang();
	
	public Setlore() {
		super("");
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!sender.hasPermission("minicore.setlore")) {
			sender.sendMessage(lang.Prefix + lang.NoPerm);
			return false;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(lang.OnlyIngame);
			return false;
		}
		
		Player p = (Player)sender;
		
		if (args.length == 0) {
			p.sendMessage(lang.Prefix + lang.InvalidArgs);
			
		} else {
			StringHandler lore = new StringHandler(args, ' ');
			if (p.hasPermission("minicore.setlore.color")) {
				lore.replaceColors();
			}

			BaseItem item = BaseItem.from(p.getInventory().getItemInMainHand());

			if (!item.getType().equals(Material.AIR)) {
				item.setLore(lore.split('|'));
				p.updateInventory();
				p.sendMessage(lang.Prefix + lang.LoreChanged);
				Effect.sound(p, Settings.Sound.modified());

			} else {
				p.sendMessage(lang.Prefix + lang.InvalidItem);
			}

		}
		
		return false;
	}
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return null;
	}
}
