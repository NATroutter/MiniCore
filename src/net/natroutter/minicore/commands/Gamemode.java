package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.minicore.utilities.Utils;

import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Gamemode extends Command {

	Lang lang = MiniCore.getLang();

	public Gamemode() {
		super("");
		this.setAliases(Collections.singletonList("gm"));
	}


	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(lang.InvalidArgs);
				return false;
			}
			Player p = (Player)sender;

			if (p.hasPermission("minicore.gamemode")) {
				
				GameMode gm = Utils.ValidateGamemode(args[0]);
				if (gm != null) {
					
					StringHandler message = new StringHandler(lang.GamemodeChanged);
					message.setPrefix(lang.Prefix);
					message.replace("{gamemode}", Utils.getGamemodeName(gm));
					
					p.setGameMode(gm);
					message.send(p);
					Effect.sound(p, Settings.Sound.gamemode());
					Effect.particle(Settings.Particle.gamemode(p.getLocation()));
					
				} else {
					p.sendMessage(lang.Prefix + lang.InvalidGamemode);
				}
				
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
		} else if (args.length == 2) {
			if (sender.hasPermission("minicore.gamemode.other")) {

				Player target = Bukkit.getPlayer(args[1]);
				if (target == null || !target.isOnline()) {
					sender.sendMessage(lang.Prefix + lang.InvalidPlayer);
					return false;
				}
			
				GameMode gm = Utils.ValidateGamemode(args[0]);
				if (gm != null) {

					StringHandler message = new StringHandler(lang.GamemodeChanged);
					message.setPrefix(lang.Prefix);
					message.replace("{gamemode}", Utils.getGamemodeName(gm));

					StringHandler message1 = new StringHandler(lang.GamemodeChangedOther);
					message1.setPrefix(lang.Prefix);
					message1.replace("{player}", target.getName());
					message1.replace("{gamemode}", Utils.getGamemodeName(gm));

					if (sender instanceof Player) {
						Player p = (Player)sender;
						if (!target.getUniqueId().equals(p.getUniqueId())) {
							Effect.sound(p, Settings.Sound.gamemode());
							Effect.sound(target, Settings.Sound.gamemode());

							Effect.particle(Settings.Particle.gamemode(target.getLocation()));

							target.setGameMode(gm);
							message.send(target);
							message1.send(p);
						} else {
							p.setGameMode(gm);
							message.send(p);
							Effect.sound(p, Settings.Sound.gamemode());
							Effect.particle(Settings.Particle.gamemode(p.getLocation()));
						}
					} else {
						Effect.sound(target, Settings.Sound.gamemode());

						Effect.particle(Settings.Particle.gamemode(target.getLocation()));

						target.setGameMode(gm);
						message.send(target);
						sender.sendMessage(message1.build());
					}

				} else {
					sender.sendMessage(lang.Prefix + lang.InvalidGamemode);
				}
				
			} else {
				sender.sendMessage(lang.Prefix + lang.NoPerm);
			}
		} else {
			sender.sendMessage(lang.Prefix + lang.ToomanyArgs);
		}
		
		return false;
	}

	public List<String> gamemodeNames(){
		return Arrays.asList("0", "1", "2", "3", "s", "c", "a", "sp", "survival", "creative", "adventure", "spectator");
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (args.length == 1) {
			return gamemodeNames();
		} else if (args.length == 2) {
			return Utils.playerNameList();
		}
		return null;
	}

}
