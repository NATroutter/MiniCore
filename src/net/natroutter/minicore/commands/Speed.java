package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.objects.SoundSettings;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.TraderLlama;

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
	}
	
	private boolean ValidSpeed(BasePlayer p, String speed) {
		try {
			int ParsedSpeed = Integer.parseInt(speed);
			if (utils.isBetween(ParsedSpeed, 1, 10)) {
				return false;
			} else {
				p.sendMessage(lang.Prefix + lang.SpeedOutOfRange);
			}
		} catch(Exception e) {
			p.sendMessage(lang.Prefix + lang.InvalidSpeed);
		}
		return true;
	}
	
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(lang.OnlyIngame);
			return false;
		}
		
		BasePlayer p = BasePlayer.from(sender);
		if (args.length == 0) {
			if (p.hasPermission("minicore.speed")) {
				p.sendMessage(lang.Prefix + lang.SpeedNotDefined);
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
			
			
		} else if (args.length == 1) {
			
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
			if (p.hasPermission("minicore.speed.other")) {
				p.sendMessage(lang.Prefix + lang.InvalidArgs);
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
			
		} else if (args.length == 3) {
			if (p.hasPermission("minicore.speed.other")) {
				
				BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[0]));
				if (target == null || !target.isOnline()) {
					p.sendMessage(lang.Prefix + lang.InvalidPlayer);
					return false;
				}
				
				SpeedType type = SpeedType.getType(args[1]);
				if (type == null) {
					p.sendMessage(lang.Prefix + lang.InvalidSpeedType);
					return false;
				}
				
				if (ValidSpeed(p, args[2])) { return false; }
				Integer speed = Integer.valueOf(args[2]);
						
				
				StringHandler message = new StringHandler(lang.SpeedChangedOther);
				message.setPrefix(lang.Prefix);
				message.replaceAll("{player}", target.getName());
				message.replaceAll("{type}", type.getName());
				message.replaceAll("{speed}", speed.toString());

				if (!target.getUniqueId().equals(p.getUniqueId())) {
					ChangeSpeed(target, type, speed);
					message.send(p);
				} else {
					ChangeSpeed(p, type, speed);
				}
				Effect.sound(target, Settings.Sound.speed());
				Effect.sound(p, Settings.Sound.speed());
				Effect.particle(Settings.Particle.speed(p.getLocation()));
				return true;
				
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
		} else {
			p.sendMessage(lang.Prefix + lang.ToomanyArgs);
		}
		
		return false;
	}
	
}
