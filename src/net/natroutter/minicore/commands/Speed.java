package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.minicore.objects.Particles;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.Utils;

import net.natroutter.natlibs.handlers.LangHandler.language.LangManager;
import net.natroutter.natlibs.utilities.StringHandler;
import net.natroutter.natlibs.utilities.Utilities;
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

public class Speed extends Command {

	private LangManager lang;
	private Effects effects;
	private Utilities utilities;
	private Utils utils;

	public Speed(Handler handler) {
		super("Speed");
		lang = handler.getLang();
		effects = handler.getEffects();
		utilities = handler.getUtilities();
		utils = handler.getUtils();
	}

	private enum SpeedType {
		Flying,
		Walking;

		public String getName(LangManager lang) {
			switch (this) {
				case Flying -> lang.get(Translations.SpeedTypes_Flying);
				case Walking -> lang.get(Translations.SpeedTypes_Walking);
			}
			return null;
		}
		public boolean isFlying() {
			return this.equals(Flying);
		}
		
		public static SpeedType getType(String type) {
			if (type.equalsIgnoreCase(Walking.name())) {
				return Walking;
			} else if (type.equalsIgnoreCase(Flying.name())) {
				return Flying;
			}
			return null;
		}
		
	}
	
	private void ChangeSpeed(Player p, SpeedType type, Integer speed) {
		StringHandler message = new StringHandler(lang.get(Translations.SpeedChanged));
		message.setPrefix(lang.get(Translations.Prefix));
		message.replaceAll("%type%", type.getName(lang));
		message.replaceAll("%speed%", speed);
		
		Float parsedSpeed = utilities.parseSpeed(speed, type.isFlying());
		if (type.isFlying()) {
			p.setFlySpeed(parsedSpeed);
		} else {
			p.setWalkSpeed(parsedSpeed);
		}
		
		message.send(p);
	}
	
	private boolean ValidSpeed(CommandSender sender, String speed) {
		try {
			int ParsedSpeed = Integer.parseInt(speed);
			if (utilities.isBetween(ParsedSpeed, 1, 10)) {
				return false;
			} else {
				lang.send(sender, Translations.Prefix, Translations.SpeedOutOfRange);
			}
		} catch(Exception e) {
			lang.send(sender, Translations.Prefix, Translations.InvalidPlayer);
		}
		return true;
	}
	
	
	@Override
	public boolean execute(CommandSender sender, String cmdLabel, String[] args) {


		if (args.length == 0) {
			if (sender.hasPermission("minicore.speed")) {
				lang.send(sender, Translations.Prefix, Translations.SpeedNotDefined);
			} else {
				lang.send(sender, Translations.Prefix, Translations.NoPerm);
			}

		} else if (args.length == 1) {
			if (!(sender instanceof Player p)) {
				lang.send(sender, Translations.Prefix, Translations.InvalidArgs);
				return false;
			}

			if (p.hasPermission("minicore.speed")) {
				
				if (ValidSpeed(p, args[0])) { return false; }
				Integer speed = Integer.valueOf(args[0]);
						
				if (p.isFlying()) {
					ChangeSpeed(p, SpeedType.Flying, speed);
					return true;
				}
				ChangeSpeed(p, SpeedType.Walking, speed);
				effects.sound(p, Sounds.Speed);
				effects.particle(p, Particles.Speed);
				return true;
				
			} else {
				lang.send(p, Translations.Prefix, Translations.NoPerm);
			}
			
		} else if (args.length == 2) {
			if (sender.hasPermission("minicore.speed.other")) {
				lang.send(sender, Translations.Prefix, Translations.InvalidArgs);
			} else {
				lang.send(sender, Translations.Prefix, Translations.NoPerm);
			}
			
		} else if (args.length == 3) {
			if (sender.hasPermission("minicore.speed.other")) {

				if (ValidSpeed(sender, args[0])) { return false; }
				Integer speed = Integer.valueOf(args[0]);

				SpeedType type = SpeedType.getType(args[1]);
				if (type == null) {
					lang.send(sender, Translations.Prefix, Translations.InvalidSpeedType);
					return false;
				}

				Player target = Bukkit.getPlayer(args[2]);
				if (target == null || !target.isOnline()) {
					lang.send(sender, Translations.Prefix, Translations.InvalidPlayer);
					return false;
				}

				StringHandler message = new StringHandler(lang.get(Translations.SpeedChangedOther));
				message.setPrefix(lang.get(Translations.Prefix));
				message.replaceAll("%player%", target.getName());
				message.replaceAll("%type%", type.getName(lang));
				message.replaceAll("%speed%", speed.toString());

				if (sender instanceof Player p) {
					if (!target.getUniqueId().equals(p.getUniqueId())) {

						ChangeSpeed(target, type, speed);
						message.send(p);

						effects.sound(target, Sounds.Speed);
						effects.sound(p, Sounds.Speed);
						effects.particle(target, Particles.Speed);
					} else {
						if (p.isFlying()) {
							ChangeSpeed(p, SpeedType.Flying, speed);
							return true;
						}
						ChangeSpeed(p, SpeedType.Walking, speed);
						effects.sound(p, Sounds.Speed);
						effects.particle(p, Particles.Speed);
					}
				} else {
					ChangeSpeed(target, type, speed);
					sender.sendMessage(message.build());
					effects.sound(target, Sounds.Speed);
					effects.particle(target, Particles.Speed);
				}
				return true;
			} else {
				lang.send(sender, Translations.Prefix, Translations.NoPerm);
			}
		} else {
			lang.send(sender, Translations.Prefix, Translations.ToomanyArgs);
		}
		
		return false;
	}

	public List<String> SpeedValues(){
		List<String> speeds = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			speeds.add(String.valueOf(i));
		}
		return speeds;
	}
	public List<String> SpeedTypeNames(){
		return Arrays.asList("Flying","Walking");
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (args.length == 1) {
			List<String> shorted = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], SpeedValues(), shorted);
			Collections.sort(shorted);
			return shorted;
		} else if (args.length == 2) {
			List<String> shorted = new ArrayList<>();
			StringUtil.copyPartialMatches(args[1], SpeedTypeNames(), shorted);
			Collections.sort(shorted);
			return shorted;
		} else if (args.length == 3) {
			List<String> shorted = new ArrayList<>();
			StringUtil.copyPartialMatches(args[2], utils.playerNameList(), shorted);
			Collections.sort(shorted);
			return shorted;
		}
		return null;
	}

}
