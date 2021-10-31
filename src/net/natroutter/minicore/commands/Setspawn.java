package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Setspawn extends Command {

	private final Lang lang = MiniCore.getLang();
	private final YamlDatabase database = MiniCore.getYamlDatabase();
	
	public Setspawn() {
		super("");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!sender.hasPermission("minicore.setspawn")) {
			sender.sendMessage(lang.Prefix + lang.NoPerm);
			return false;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(lang.OnlyIngame);
			return false;
		}
		
		Player p = (Player)sender;
		if (args.length == 0) {
			database.saveLoc("General", "SpawnLoc", p.getLocation());
			p.getWorld().setSpawnLocation(p.getLocation());
			p.sendMessage(lang.Prefix + lang.SpawnSet);
			Effect.sound(p, Settings.Sound.setspawn());
			Effect.particle(Settings.Particle.Success(p.getLocation()));
		} else {
			p.sendMessage(lang.Prefix + lang.ToomanyArgs);
		}
		
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return null;
	}
}
