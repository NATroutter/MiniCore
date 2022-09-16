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
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fly extends Command {

	private LangManager lang;
	private Effects effects;
	private Utils utils;

	public Fly(Handler handler) {
		super("Fly");
		lang = handler.getLang();
		effects = handler.getEffects();
		utils = handler.getUtils();
	}
	
	private void ToggleFly(Player p, boolean state) {
		StringHandler message = new StringHandler(lang.get(Translations.ToggleFly));
		message.setPrefix(lang.get(Translations.Prefix));
		message.replaceAll("%state%", getState(state));

		p.setAllowFlight(state);
		p.setFlying(state);
		message.send(p);
	}
	
	private String getState(boolean state) {
		return state ? lang.get(Translations.ToggleStates_Enabled) : lang.get(Translations.ToggleStates_Disabled);
	}

	
	@Override
	public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player p)) {
				lang.send(sender, Translations.Prefix, Translations.InvalidArgs);
				return false;
			}

			if (p.hasPermission("minicore.fly")) {
				ToggleFly(p, !p.getAllowFlight());
				effects.sound(p, Sounds.Fly);
				effects.particle(p, Particles.Fly);
			} else {
				lang.send(p, Translations.Prefix, Translations.NoPerm);
			}
		} else if (args.length == 1) {
			if (sender.hasPermission("minicore.fly.other")) {

				Player target = Bukkit.getPlayer(args[0]);
				if (target == null || !target.isOnline()) {
					lang.send(sender, Translations.Prefix, Translations.InvalidPlayer);
					return false;
				}

				boolean newState = !target.getAllowFlight();

				StringHandler message = new StringHandler(lang.get(Translations.ToggleFlyOther));
				message.setPrefix(lang.get(Translations.Prefix));
				message.replaceAll("%player%", target.getName());
				message.replaceAll("%state%", getState(newState));

				if (sender instanceof Player p) {
					if (!target.getUniqueId().equals(p.getUniqueId())) {

						message.send(p);
						ToggleFly(target, newState);
						effects.sound(p, Sounds.Fly);
						effects.sound(target, Sounds.Fly);

						effects.particle(target, Particles.Fly);
					} else {
						ToggleFly(p, !p.getAllowFlight());
						effects.sound(p, Sounds.Fly);
						effects.particle(p, Particles.Fly);
					}
				} else {
					sender.sendMessage(message.build());

					ToggleFly(target, newState);
					effects.sound(target, Sounds.Fly);
					effects.particle(target, Particles.Fly);
				}
				
			} else {
				lang.send(sender, Translations.Prefix, Translations.NoPerm);
			}
		} else {
			lang.send(sender, Translations.Prefix, Translations.ToomanyArgs);
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
