package net.natroutter.minicore.commands;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.handlers.Database.handlers.PlayerDataHandler;
import net.natroutter.minicore.handlers.Database.tables.PlayerData;
import net.natroutter.minicore.utilities.*;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class God extends Command {

    public God() {
        super("");
    }

    private final Lang lang = MiniCore.getLang();
    private final Config config = MiniCore.getConf();

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(lang.InvalidArgs);
                return false;
            }
            BasePlayer p = BasePlayer.from(sender);

            if (p.hasPermission("minicore.god")) {
                PlayerData data = PlayerDataHandler.queryForID(p.getUniqueId());
                if (data == null) {
                    return false;
                }
                boolean newstate = !data.getGod();
                data.setGod(newstate);
                StringHandler message = new StringHandler(lang.GodToggled).setPrefix(lang.Prefix);
                message.replaceAll("{status}", newstate ? lang.ToggleStates.enabled : lang.ToggleStates.disabled);
                message.send(p);
                PlayerDataHandler.updateForID(data);
                Effect.sound(p, Settings.Sound.god());
                Effect.particle(Settings.Particle.God(p.getLocation()));
            } else {
                p.sendMessage(lang.Prefix + lang.NoPerm);
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("minicore.god.other")) {
                BasePlayer target = BasePlayer.from(Bukkit.getPlayer(args[0]));
                if (target == null || !target.isOnline()) {
                    sender.sendMessage(lang.Prefix + lang.InvalidPlayer);
                    return false;
                }

                PlayerData data = PlayerDataHandler.queryForID(target.getUniqueId());
                if (data == null) {
                    return false;
                }
                boolean newstate = !data.getGod();
                data.setGod(newstate);

                StringHandler message = new StringHandler(lang.GodToggledOther).setPrefix(lang.Prefix);
                message.replaceAll("{status}", newstate ? lang.ToggleStates.enabled : lang.ToggleStates.disabled);
                message.replaceAll("{player}", target.getName());

                if (sender instanceof Player) {
                    BasePlayer p = BasePlayer.from(sender);
                    if (!target.getUniqueId().equals(p.getUniqueId())) {
                        message.send(p);
                        PlayerDataHandler.updateForID(data);
                        Effect.sound(target, Settings.Sound.god());
                        Effect.sound(p, Settings.Sound.god());
                        Effect.particle(Settings.Particle.God(target.getLocation()));
                    } else {
                        message.send(p);
                        PlayerDataHandler.updateForID(data);
                        Effect.sound(p, Settings.Sound.god());
                        Effect.particle(Settings.Particle.God(target.getLocation()));
                    }
                } else {
                    sender.sendMessage(message.build());
                    PlayerDataHandler.updateForID(data);
                    Effect.sound(target, Settings.Sound.god());
                    Effect.particle(Settings.Particle.God(target.getLocation()));
                }
            } else {
                sender.sendMessage(lang.Prefix+lang.NoPerm);
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