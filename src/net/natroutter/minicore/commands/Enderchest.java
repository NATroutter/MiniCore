package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Enderchest extends Command {

	Lang lang = MiniCore.getLang();

	public Enderchest() {
		super("");
		this.setAliases(Arrays.asList("ec", "echest"));
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(lang.OnlyIngame);
			return false;
		}
		
		BasePlayer p = BasePlayer.from(sender);
		
		if (args.length == 0) {
			if (p.hasPermission("minicore.enderchest")) {
			
				p.openInventory(p.getEnderChest());
				p.sendMessage(lang.Prefix + lang.EnderChestOpened);
				
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
			Effect.sound(p, Settings.Sound.enderChest());
			
		} else if (args.length == 1) {
			if (p.hasPermission("minicore.enderchest.other")) {
				
				BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[0]));
				if (target == null || !target.isOnline()) {
					p.sendMessage(lang.Prefix + lang.InvalidPlayer);
					return false;
				}

				if (!p.getUniqueId().equals(target.getUniqueId())) {
					p.openInventory(target.getEnderChest());
					StringHandler message = new StringHandler(lang.EnderChestOpenedOther);
					message.setPrefix(lang.Prefix);
					message.replaceAll("{player}", target.getName());
					message.send(p);
				} else {
					p.openInventory(p.getEnderChest());
					p.sendMessage(lang.Prefix + lang.EnderChestOpened);
				}
				Effect.sound(p, Settings.Sound.enderChest());
				
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
			
		} else {
			p.sendMessage(lang.Prefix + lang.ToomanyArgs);
		}
		
		return false;
	}
	
}
