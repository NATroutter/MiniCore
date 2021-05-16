package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.objects.ParticleSettings;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Bukkit;
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
	private final YamlDatabase database = MiniCore.getYamlDatabase();

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
				Effect.particle(Settings.Particle.teleportStart(p.getLocation()));
				p.teleport(loc);
				Effect.particle(Settings.Particle.teleportEnd(loc));
				Effect.sound(p, Settings.Sound.teleported());

				p.playSound(p.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 50);
				p.sendMessage(lang.Prefix + lang.TeleportedToSpawn);
			} else {
				p.sendMessage(lang.Prefix + lang.SpawnNotset);
			}

		} else if (args.length == 1) {
			BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[0]));
			if (target == null || !target.isOnline()) {
				p.sendMessage(lang.Prefix + lang.InvalidPlayer);
				return false;
			}

			Location loc = database.getLocation("General", "SpawnLoc");

			if (loc != null) {
				Effect.particle(Settings.Particle.teleportStart(target.getLocation()));
				target.teleport(loc);
				Effect.particle(Settings.Particle.teleportEnd(loc));
				Effect.sound(p, Settings.Sound.teleported());
				Effect.sound(target, Settings.Sound.teleported());

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
