package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.objects.Particles;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.handlers.database.YamlDatabase;
import net.natroutter.natlibs.handlers.langHandler.language.LangManager;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Spawn extends Command {

	private LangManager lang;
	private Config config;
	private Effects effects;
	private YamlDatabase database;
	private Utils utils;

	public Spawn(Handler handler) {
		super("Spawn");
		lang = handler.getLang();
		config = handler.getConfig();
		effects = handler.getEffects();
		database = handler.getYamlDatabase();
		utils = handler.getUtils();
	}

	@Override
	public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player p)) {
				lang.send(sender, Translations.Prefix, Translations.InvalidArgs);
				return false;
			}
			if (p.hasPermission("minicore.spawn")) {
				Location loc = database.getLocation("General", "SpawnLoc");

				if (loc != null) {
					effects.particle(p, Particles.TeleportStart);
					p.teleport(loc);
					effects.particle(loc, Particles.TeleportEnd);
					effects.sound(p, Sounds.Teleported);

					lang.send(sender, Translations.Prefix, Translations.TeleportedToSpawn);
				} else {
					lang.send(sender, Translations.Prefix, Translations.SpawnNotset);
				}
			}

		} else if (args.length == 1) {
			if (sender.hasPermission("minicore.spawn.other")) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null || !target.isOnline()) {
					lang.send(sender, Translations.Prefix, Translations.InvalidPlayer);
					return false;
				}

				Location loc = database.getLocation("General", "SpawnLoc");
				if (loc != null) {

					if (sender instanceof Player p) {
						effects.sound(p, Sounds.Teleported);

						if (!target.getUniqueId().equals(p.getUniqueId())) {
							effects.particle(target, Particles.TeleportStart);
							target.teleport(loc);
							effects.sound(target, Sounds.Teleported);
							StringHandler message = new StringHandler(lang.get(Translations.TeleportedToSpawnOther)).setPrefix(lang.get(Translations.Prefix));
							message.replaceAll("%player%", target.getName());
							message.send(p);
						} else {
							effects.particle(p, Particles.TeleportStart);
							lang.send(p, Translations.Prefix, Translations.TeleportedToSpawn);
							p.teleport(loc);
						}

						effects.particle(loc, Particles.TeleportEnd);
					}

				} else {
					lang.send(sender, Translations.Prefix, Translations.SpawnNotset);
				}
			} else {
				lang.send(sender, Translations.Prefix, Translations.NoPerm);
			}
		} else {
			lang.send(sender, Translations.Prefix, Translations.ToomanyArgs);
		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (args.length == 1) {
			List<String> shorted = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], utils.playerNameList(), shorted);
			Collections.sort(shorted);
			return shorted;
		}
		return null;
	}
}
