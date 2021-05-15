package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class Invsee extends Command {

	Lang lang = MiniCore.getLang();

	public Invsee() {
		super("");
		this.setAliases(Collections.singletonList("openinv"));
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(lang.OnlyIngame);
			return false;
		}
		
		BasePlayer p = BasePlayer.from(sender);
		
		if (args.length == 0) {
			p.sendMessage(lang.Prefix + lang.InvalidPlayer);
			
		} else if (args.length == 1) {
			if (p.hasPermission("minicore.invsee")) {
				
				BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[0]));
				if (target == null || !target.isOnline()) {
					p.sendMessage(lang.Prefix + lang.InvalidPlayer);
					return false;
				}
				
				p.openInventory(target.getInventory());
				if (!p.getUniqueId().equals(target.getUniqueId())) {
					StringHandler message = new StringHandler(lang.PlayerInvOpened);
					message.setPrefix(lang.Prefix);
					message.replaceAll("{player}", target.getName());
					message.send(p);
				}
				
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
			
		} else {
			p.sendMessage(lang.Prefix + lang.ToomanyArgs);
		}
		
		return false;
	}
	
}
