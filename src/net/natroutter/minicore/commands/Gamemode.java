package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class Gamemode extends Command {

	Lang lang = MiniCore.getLang();

	public Gamemode() {
		super("");
		this.setAliases(Collections.singletonList("gm"));
	}
	
	private GameMode ValidateGamemode(String gm) {
		switch(gm.toLowerCase()) {
			case "s": case "0": case "survival":
				return GameMode.SURVIVAL;
			case "c": case "1": case "creative":
				return GameMode.CREATIVE;
			case "a": case "2": case "adventure":
				return GameMode.ADVENTURE;
			case "sp": case "3": case "spectator":
				return GameMode.SPECTATOR;
			default:
				return null;
		}
	}
	
	private String getGamemodeName(GameMode gm) {
		switch(gm) {
			case SURVIVAL:
				return lang.GameModes.Survival;
			case CREATIVE:
				return lang.GameModes.Creative;
			case ADVENTURE:
				return lang.GameModes.Adventure;
			case SPECTATOR:
				return lang.GameModes.Spectator;
			default:
				return null;
		}
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(lang.OnlyIngame);
			return false;
		}
		
		BasePlayer p = BasePlayer.from(sender);
		
		if (args.length == 1) {
			if (p.hasPermission("minicore.gamemode")) {
				
				GameMode gm = ValidateGamemode(args[0]);
				if (gm != null) {
					
					StringHandler message = new StringHandler(lang.GamemodeChanged);
					message.setPrefix(lang.Prefix);
					message.replace("{gamemode}", getGamemodeName(gm));
					
					p.setGameMode(gm);
					message.send(p);
					
				} else {
					p.sendMessage(lang.Prefix + lang.InvalidGamemode);
				}
				
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
		} else if (args.length == 2) {
			if (p.hasPermission("minicore.gamemode.other")) {
				
				BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[1]));
				if (target == null || !target.isOnline()) {
					p.sendMessage(lang.Prefix + lang.InvalidPlayer);
					return false;
				}
			
				GameMode gm = ValidateGamemode(args[0]);
				if (gm != null) {
					
					StringHandler message = new StringHandler(lang.GamemodeChanged);
					message.setPrefix(lang.Prefix);
					message.replace("{gamemode}", getGamemodeName(gm));
					
					StringHandler message1 = new StringHandler(lang.GamemodeChangedOther);
					message1.setPrefix(lang.Prefix);
					message1.replace("{player}", target.getName());
					message1.replace("{gamemode}", getGamemodeName(gm));
					
					p.setGameMode(gm);
					message.send(target);
					if (!target.getUniqueId().equals(p.getUniqueId())) {
						message1.send(p);
					}
					
				} else {
					p.sendMessage(lang.Prefix + lang.InvalidGamemode);
				}
				
			} else {
				p.sendMessage(lang.Prefix + lang.NoPerm);
			}
		} else {
			p.sendMessage(lang.Prefix + lang.ToomanyArgs);
		}
		
		return false;
	}
	
}
