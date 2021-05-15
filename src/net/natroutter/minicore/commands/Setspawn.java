package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.objects.BasePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Setspawn extends Command {

	private final Lang lang = MiniCore.getLang();
	private final YamlDatabase database = MiniCore.getYamlDatabase();
	
	public Setspawn() {
		super("");
		this.setPermission("hubcore.setspawn");
		this.setPermissionMessage(lang.Prefix + lang.NoPerm);
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(lang.OnlyIngame);
			return false;
		}
		
		BasePlayer p = BasePlayer.from(sender);
		if (args.length == 0) {
			database.saveLoc("General", "SpawnLoc", p.getLocation());
			p.getWorld().setSpawnLocation(p.getLocation());
			p.sendMessage(lang.Prefix + lang.SpawnSet);
		} else {
			p.sendMessage(lang.Prefix + lang.ToomanyArgs);
		}
		
		return false;
	}

	
}
