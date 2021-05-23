package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

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
		Player p = (Player) sender;

		if (args.length == 0) {
			p.sendMessage(lang.Prefix + lang.InvalidPlayer);
			
		} else if (args.length == 1) {
			if (p.hasPermission("minicore.invsee")) {

				Player target = Bukkit.getPlayer(args[0]);
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
				} else {
					p.sendMessage(lang.Prefix + lang.CantTargetYourSelf);
				}

				Effect.sound(p, Settings.Sound.chest());
				
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
			
		} else {
			p.sendMessage(lang.Prefix + lang.ToomanyArgs);
		}
		
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (args.length == 1) {
			return Utils.playerNameList();
		}
		return null;
	}

}
