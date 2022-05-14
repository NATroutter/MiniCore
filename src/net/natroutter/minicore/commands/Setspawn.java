package net.natroutter.minicore.commands;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.minicore.objects.Particles;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;

import net.natroutter.natlibs.handlers.LangHandler.language.LangManager;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Setspawn extends Command {

	private LangManager lang;
	private YamlDatabase database;
	private Effects effects;

	public Setspawn(Handler handler) {
		super("Setspawn");
		lang = handler.getLang();
		database = handler.getYamlDatabase();
		effects = handler.getEffects();
	}

	@Override
	public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
		if (!(sender instanceof Player p)) {
			lang.send(sender, Translations.Prefix, Translations.OnlyIngame);
			return false;
		}

		if (!sender.hasPermission("minicore.setspawn")) {
			lang.send(sender, Translations.Prefix, Translations.NoPerm);
			return false;
		}

		if (args.length == 0) {
			database.saveLoc("General", "SpawnLoc", p.getLocation());
			p.getWorld().setSpawnLocation(p.getLocation());
			lang.send(sender, Translations.Prefix, Translations.SpawnSet);
			effects.sound(p, Sounds.SetSpawn);
			effects.particle(p, Particles.Success);
		} else {
			lang.send(p, Translations.Prefix, Translations.ToomanyArgs);
		}
		
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return null;
	}
}
