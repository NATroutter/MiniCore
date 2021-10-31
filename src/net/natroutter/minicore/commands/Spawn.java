package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.utilities.Effect;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Settings;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;

import net.natroutter.natlibs.objects.ParticleSettings;
import net.natroutter.natlibs.utilities.StringHandler;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Spawn extends Command {

	public Spawn() {
		super("");
	}

	private final Lang lang = MiniCore.getLang();
	private final YamlDatabase database = MiniCore.getYamlDatabase();

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(lang.InvalidArgs);
				return false;
			}
			Player p = (Player)sender;
			if (p.hasPermission("minicore.spawn")) {
				Location loc = database.getLocation("General", "SpawnLoc");

				if (loc != null) {
					Effect.particle(Settings.Particle.teleportStart(p.getLocation()));
					p.teleport(loc);
					Effect.particle(Settings.Particle.teleportEnd(loc));
					Effect.sound(p, Settings.Sound.teleported());

					p.sendMessage(lang.Prefix + lang.TeleportedToSpawn);
				} else {
					p.sendMessage(lang.Prefix + lang.SpawnNotset);
				}
			}

		} else if (args.length == 1) {
			if (sender.hasPermission("minicore.spawn.other")) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null || !target.isOnline()) {
					sender.sendMessage(lang.Prefix + lang.InvalidPlayer);
					return false;
				}

				Location loc = database.getLocation("General", "SpawnLoc");
				if (loc != null) {

					if (sender instanceof Player) {
						Player p = (Player)sender;
						Effect.sound(p, Settings.Sound.teleported());

						if (!target.getUniqueId().equals(p.getUniqueId())) {
							Effect.particle(Settings.Particle.teleportStart(target.getLocation()));
							target.teleport(loc);
							Effect.sound(target, Settings.Sound.teleported());
							StringHandler message = new StringHandler(lang.TeleportedToSpawnOther).setPrefix(lang.Prefix);
							message.replaceAll("{player}", target.getName());
							message.send(p);
						} else {
							Effect.particle(Settings.Particle.teleportStart(p.getLocation()));
							p.sendMessage(lang.Prefix + lang.TeleportedToSpawn);
							p.teleport(loc);
						}

						Effect.particle(Settings.Particle.teleportEnd(loc));
					}

				} else {
					sender.sendMessage(lang.Prefix + lang.SpawnNotset);
				}
			} else {
				sender.sendMessage(lang.Prefix + lang.NoPerm);
			}
		} else {
			sender.sendMessage(lang.Prefix + lang.ToomanyArgs);
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
