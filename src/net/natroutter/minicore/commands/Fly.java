package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.features.InfoHandler;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Fly extends Command {

	private final Lang lang = MiniCore.getLang();
	
	public Fly() {
		super("");
	}
	
	private void ToggleFly(BasePlayer p, boolean state) {
		StringHandler message = new StringHandler(lang.ToggleFly);
		message.setPrefix(lang.Prefix);
		message.replaceAll("{state}", getState(state));

		p.setAllowFlight(state);
		p.setFlying(state);
		message.send(p);
		InfoHandler.updatePlayer(p);
	}
	
	private String getState(boolean state) {
		return state ? lang.ToggleStates.enabled : lang.ToggleStates.disabled;
	}

	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(lang.OnlyIngame);
			return false;
		}
		
		BasePlayer p = BasePlayer.from(sender);
		
		if (args.length == 0) {
			if (p.hasPermission("minicore.fly")) {
				ToggleFly(p, !p.getAllowFlight());
				Effect.sound(p, Settings.Sound.fly());
				Effect.particle(Settings.Particle.fly(p.getLocation()));
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
		} else if (args.length == 1) {
			if (p.hasPermission("minicore.fly.other")) {
				
				BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[0]));
				if (target == null || !target.isOnline()) {
					p.sendMessage(lang.Prefix + lang.InvalidPlayer);
					return false;
				}
			
				if (!target.getUniqueId().equals(p.getUniqueId())) {
					boolean newState = !target.getAllowFlight();

					StringHandler message = new StringHandler(lang.ToggleFlyOther);
					message.setPrefix(lang.Prefix);
					message.replaceAll("{player}", target.getName());
					message.replaceAll("{state}", getState(newState));

					if (!target.getUniqueId().equals(p.getUniqueId())) {
						message.send(p);
					}
					ToggleFly(target, newState);
					Effect.sound(p, Settings.Sound.fly());
					Effect.sound(target, Settings.Sound.fly());

					Effect.particle(Settings.Particle.fly(target.getLocation()));
				} else {
					ToggleFly(p, !p.getAllowFlight());
					Effect.sound(p, Settings.Sound.fly());
					Effect.particle(Settings.Particle.fly(p.getLocation()));
				}
				
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
