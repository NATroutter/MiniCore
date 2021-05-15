package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.natlibs.objects.BaseItem;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Rename extends Command {

	Lang lang = MiniCore.getLang();

	public Rename() {
		super("");
		this.setPermission("minicore.rename");
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
			StringHandler name = new StringHandler(args, ' ');
			BaseItem item = BaseItem.from(p.getInventory().getItemInMainHand());

			if (!item.getType().equals(Material.AIR)) {
				item.setDisplayName(name.build());
				p.updateInventory();
				p.sendMessage(lang.Prefix + lang.ItemRenamed);

			} else {
				p.sendMessage(lang.Prefix + lang.InvalidItem);
			}
		}
		
		return false;
	}
	
}
