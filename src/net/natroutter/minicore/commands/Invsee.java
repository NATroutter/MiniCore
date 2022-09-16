package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.handlers.langHandler.language.LangManager;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Invsee extends Command {

	private LangManager lang;
	private Effects effects;
	private Utils utils;

	public Invsee(Handler handler) {
		super("Invsee");
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

		if (args.length == 0) {
			lang.send(p, Translations.Prefix, Translations.InvalidPlayer);
			
		} else if (args.length == 1) {
			if (p.hasPermission("minicore.invsee")) {

				Player target = Bukkit.getPlayer(args[0]);
				if (target == null || !target.isOnline()) {
					lang.send(p, Translations.Prefix, Translations.InvalidPlayer);
					return false;
				}

				if (!p.getUniqueId().equals(target.getUniqueId())) {
					StringHandler message = new StringHandler(lang.get(Translations.PlayerInvOpened));
					message.setPrefix(lang.get(Translations.Prefix));
					message.replaceAll("%player%", target.getName());
					message.send(p);

					p.openInventory(target.getInventory());
					effects.sound(p, Sounds.Chest);

				} else {
					lang.send(p, Translations.Prefix, Translations.CantTargetYourSelf);
				}
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
