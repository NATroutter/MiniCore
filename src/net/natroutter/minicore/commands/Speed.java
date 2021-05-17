package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.features.InfoHandler;
import net.natroutter.minicore.objects.SoundSettings;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Player;
import org.bukkit.entity.TraderLlama;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Speed extends Command {

	private static final Lang lang = MiniCore.getLang();
	private final Utilities utils = MiniCore.getUtilities();
	
	public Speed() {
		super("");
	}

	private enum SpeedType {
		Flying(true, lang.SpeedTypes.Flying),
		Walking(false, lang.SpeedTypes.Walking);

		private final boolean fly;
		private final String name;
		SpeedType(boolean fly, String name) {
			this.fly = fly;
			this.name = name;
		}
		public String getName() {return name;}
		public boolean isFlying() { return fly; }
		
		public static SpeedType getType(String type) {
			if (type.equalsIgnoreCase(Walking.name())) {
				return Walking;
			} else if (type.equalsIgnoreCase(Flying.name())) {
				return Flying;
			}
			return null;
		}
		
	}
	
	private void ChangeSpeed(BasePlayer p, SpeedType type, Integer speed) {
		StringHandler message = new StringHandler(lang.SpeedChanged);
		message.setPrefix(lang.Prefix);
		message.replaceAll("{type}", type.getName());
		message.replaceAll("{speed}", speed);
		
		Float parsedSpeed = utils.parseSpeed(speed, type.isFlying());
		if (type.isFlying()) {
			p.setFlySpeed(parsedSpeed);
		} else {
			p.setWalkSpeed(parsedSpeed);
		}
		
		message.send(p);
		InfoHandler.updatePlayer(p);
	}
	
	private boolean ValidSpeed(CommandSender sender, String speed) {
		try {
			int ParsedSpeed = Integer.parseInt(speed);
			if (utils.isBetween(ParsedSpeed, 1, 10)) {
				return false;
			} else {
				sender.sendMessage(lang.Prefix + lang.SpeedOutOfRange);
			}
		} catch(Exception e) {
			sender.sendMessage(lang.Prefix + lang.InvalidSpeed);
		}
		return true;
	}
	
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			if (sender.hasPermission("minicore.speed")) {
				sender.sendMessage(lang.Prefix + lang.SpeedNotDefined);
			} else {
				sender.sendMessage(lang.Prefix + lang.NoPerm);
			}

		} else if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(lang.InvalidArgs);
				return false;
			}
			BasePlayer p = BasePlayer.from(sender);

			if (p.hasPermission("minicore.speed")) {
				
				if (ValidSpeed(p, args[0])) { return false; }
				Integer speed = Integer.valueOf(args[0]);
						
				if (p.isFlying()) {
					ChangeSpeed(p, SpeedType.Flying, speed);
					return true;
				}
				ChangeSpeed(p, SpeedType.Walking, speed);
				Effect.sound(p, Settings.Sound.speed());
				Effect.particle(Settings.Particle.speed(p.getLocation()));
				return true;
				
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
			
		} else if (args.length == 2) {
			if (sender.hasPermission("minicore.speed.other")) {
				sender.sendMessage(lang.Prefix + lang.InvalidArgs);
			} else {
				sender.sendMessage(lang.Prefix + lang.NoPerm);
			}
			
		} else if (args.length == 3) {
			if (sender.hasPermission("minicore.speed.other")) {

				if (ValidSpeed(sender, args[0])) { return false; }
				Integer speed = Integer.valueOf(args[0]);

				SpeedType type = SpeedType.getType(args[1]);
				if (type == null) {
					sender.sendMessage(lang.Prefix + lang.InvalidSpeedType);
					return false;
				}

				BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[2]));
				if (target == null || !target.isOnline()) {
					sender.sendMessage(lang.Prefix + lang.InvalidPlayer);
					return false;
				}

				StringHandler message = new StringHandler(lang.SpeedChangedOther);
				message.setPrefix(lang.Prefix);
				message.replaceAll("{player}", target.getName());
				message.replaceAll("{type}", type.getName());
				message.replaceAll("{speed}", speed.toString());

				if (sender instanceof Player) {
					BasePlayer p = BasePlayer.from(sender);
					if (!target.getUniqueId().equals(p.getUniqueId())) {

						ChangeSpeed(target, type, speed);
						message.send(p);

						Effect.sound(target, Settings.Sound.speed());
						Effect.sound(p, Settings.Sound.speed());
						Effect.particle(Settings.Particle.speed(target.getLocation()));
					} else {
						if (p.isFlying()) {
							ChangeSpeed(p, SpeedType.Flying, speed);
							return true;
						}
						ChangeSpeed(p, SpeedType.Walking, speed);
						Effect.sound(p, Settings.Sound.speed());
						Effect.particle(Settings.Particle.speed(p.getLocation()));
					}
				} else {
					ChangeSpeed(target, type, speed);
					sender.sendMessage(message.build());
					Effect.sound(target, Settings.Sound.speed());
					Effect.particle(Settings.Particle.speed(target.getLocation()));
				}
				return true;
			} else {
				sender.sendMessage(lang.Prefix + lang.NoPerm);
			}
		} else {
			sender.sendMessage(lang.Prefix + lang.ToomanyArgs);
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
			return SpeedValues();
		} else if (args.length == 2) {
			return SpeedTypeNames();
		} else if (args.length == 3) {
			return Utils.playerNameList();
		}
		return null;
	}

}
