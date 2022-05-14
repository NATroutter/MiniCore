package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.handlers.LangHandler.language.LangManager;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Enderchest extends Command {

	private LangManager lang;
	private Effects effects;
	private Utils utils;

	public Enderchest(Handler handler) {
		super("Enderchest");
		this.setAliases(Arrays.asList("ec", "echest"));
		lang = handler.getLang();
		effects = handler.getEffects();
		utils = handler.getUtils();
	}
	
	@Override
	public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
		if (!(sender instanceof Player p)) {
			lang.send(sender, Translations.Prefix, Translations.OnlyIngame);
			return false;
		}

		if (!sender.hasPermission("minicore.enderchest")) {
			lang.send(sender, Translations.Prefix, Translations.NoPerm);
			return false;
		}
		
		if (args.length == 0) {
			if (p.hasPermission("minicore.enderchest")) {
			
				p.openInventory(p.getEnderChest());
				lang.send(p, Translations.Prefix, Translations.EnderChestOpened);
				
			} else {
				lang.send(p, Translations.Prefix, Translations.NoPerm);
			}
			effects.sound(p, Sounds.EnderChest);
			
		} else if (args.length == 1) {
			if (p.hasPermission("minicore.enderchest.other")) {

				Player target = Bukkit.getPlayer(args[0]);
				if (target == null || !target.isOnline()) {
					lang.send(p, Translations.Prefix, Translations.InvalidPlayer);
					return false;
				}

				if (!p.getUniqueId().equals(target.getUniqueId())) {
					p.openInventory(target.getEnderChest());
					StringHandler message = new StringHandler(lang.get(Translations.EnderChestOpenedOther));
					message.setPrefix(lang.get(Translations.Prefix));
					message.replaceAll("%player%", target.getName());
					message.send(p);
				} else {
					p.openInventory(p.getEnderChest());
					lang.send(p, Translations.Prefix, Translations.EnderChestOpened);
				}
				effects.sound(p, Sounds.EnderChest);
				
			} else {
				lang.send(p, Translations.Prefix, Translations.NoPerm);
			}
			
		} else {
			lang.send(p, Translations.Prefix, Translations.ToomanyArgs);
		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (args.length == 1) {
			List<String> shorted = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], utils.playerNameList(), shorted);
			Collections.sort(shorted);
			return shorted;
		}
		return null;
	}

}
