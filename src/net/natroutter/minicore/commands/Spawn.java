package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.objects.ParticleSettings;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn extends Command {

	public Spawn() {
		super("");
	}

	private final Lang lang = MiniCore.getLang();
	private final Utilities utils = MiniCore.getUtilities();
	private final YamlDatabase database = MiniCore.getYamlDatabase();
	
	private ParticleSettings PartSet(BasePlayer p) {
		return new ParticleSettings(Particle.DRAGON_BREATH, p.getLocation(),
				100, 0.5, 1, 0.5, 0
		);
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(lang.OnlyIngame);
			return false;
		}
		
		BasePlayer p = BasePlayer.from(sender);
		if (args.length == 0) {

			Location loc = database.getLocation("General", "SpawnLoc");

			if (loc != null) {
				utils.spawnParticleInRadius(PartSet(p), 10);
				
				p.teleport(loc);
				p.playSound(p.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 50);
				p.sendMessage(lang.Prefix + lang.TeleportedToSpawn);
			} else {
				p.sendMessage(lang.Prefix + lang.SpawnNotset);
			}
			
		} else {
			p.sendMessage(lang.Prefix + lang.ToomanyArgs);
		}
		
		
		return false;
	}

	
	
}
