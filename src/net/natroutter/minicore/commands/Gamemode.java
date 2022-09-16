package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.objects.Particles;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.minicore.utilities.Utils;

import net.natroutter.natlibs.handlers.langHandler.language.LangManager;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Gamemode extends Command {

	private LangManager lang;
	private Effects effects;
	private Utils utils;

	public Gamemode(Handler handler) {
		super("Gamemode");
		this.setAliases(Collections.singletonList("gm"));
		lang = handler.getLang();
		effects = handler.getEffects();
		utils = handler.getUtils();

	}


	
	@Override
	public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
		if (args.length == 1) {
			if (!(sender instanceof Player p)) {
				lang.send(sender, Translations.Prefix, Translations.InvalidArgs);
				return false;
			}

			if (p.hasPermission("minicore.gamemode")) {
				
				GameMode gm = utils.ValidateGamemode(args[0]);
				if (gm != null) {
					
					StringHandler message = new StringHandler(lang.get(Translations.GamemodeChanged));
					message.setPrefix(lang.get(Translations.Prefix));
					message.replace("%gamemode%", utils.getGamemodeName(gm));
					
					p.setGameMode(gm);
					message.send(p);
					effects.sound(p, Sounds.Gamemode);
					effects.particle(p, Particles.Gamemode);
					
				} else {
					lang.send(p, Translations.Prefix, Translations.InvalidGamemode);
				}
				
			} else {
				lang.send(p, Translations.Prefix, Translations.NoPerm);
			}
		} else if (args.length == 2) {
			if (sender.hasPermission("minicore.gamemode.other")) {

				Player target = Bukkit.getPlayer(args[1]);
				if (target == null || !target.isOnline()) {
					lang.send(sender, Translations.Prefix, Translations.InvalidPlayer);
					return false;
				}
			
				GameMode gm = utils.ValidateGamemode(args[0]);
				if (gm != null) {

					StringHandler message = new StringHandler(lang.get(Translations.GamemodeChanged));
					message.setPrefix(lang.get(Translations.Prefix));
					message.replace("%gamemode%", utils.getGamemodeName(gm));

					StringHandler message1 = new StringHandler(lang.get(Translations.GamemodeChangedOther));
					message1.setPrefix(lang.get(Translations.Prefix));
					message1.replace("%player%", target.getName());
					message1.replace("%gamemode%", utils.getGamemodeName(gm));

					if (sender instanceof Player p) {
						if (!target.getUniqueId().equals(p.getUniqueId())) {
							effects.sound(p, Sounds.Gamemode);
							effects.sound(target, Sounds.Gamemode);

							effects.particle(target, Particles.Gamemode);

							target.setGameMode(gm);
							message.send(target);
							message1.send(p);
						} else {
							p.setGameMode(gm);
							message.send(p);
							effects.sound(p, Sounds.Gamemode);
							effects.particle(p, Particles.Gamemode);
						}
					} else {
						effects.sound(target, Sounds.Gamemode);

						effects.particle(target, Particles.Gamemode);

						target.setGameMode(gm);
						message.send(target);
						sender.sendMessage(message1.build());
					}

				} else {
					lang.send(sender, Translations.Prefix, Translations.InvalidGamemode);
				}
				
			} else {
				lang.send(sender, Translations.Prefix, Translations.NoPerm);
			}
		} else {
			lang.send(sender, Translations.Prefix, Translations.ToomanyArgs);
		}
		
		return false;
	}

	public List<String> gamemodeNames(){
		return Arrays.asList("0", "1", "2", "3", "s", "c", "a", "sp", "survival", "creative", "adventure", "spectator");
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (args.length == 1) {
			List<String> shorted = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], gamemodeNames(), shorted);
			Collections.sort(shorted);
			return shorted;
		} else if (args.length == 2) {
			List<String> shorted = new ArrayList<>();
			StringUtil.copyPartialMatches(args[1], utils.playerNameList(), shorted);
			Collections.sort(shorted);
			return shorted;
		}
		return null;
	}

}
