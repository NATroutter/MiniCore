package net.natroutter.minicore;

import net.natroutter.minicore.commands.*;
import net.natroutter.minicore.handlers.features.*;
import net.natroutter.minicore.files.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MiniCore extends JavaPlugin {

    /*
        TODO
        Add commands:
            msg, reply, socialspy, homes tpa tpahere, tpaccept, tpdeny, signedit

        Add cooldowns for some commands with configs
        move check for teleporting

    */


    @Override
    public void onEnable() {
        Handler handler = new Handler(this);
        Config cfg = handler.getConfig();

        CommandMap map = Bukkit.getCommandMap();
        PluginManager pm = Bukkit.getPluginManager();
        String fallbackPrefix = "MiniCore";

        if (cfg.EnabledFeatures.Addlore) {map.register(fallbackPrefix, new Addlore(handler));}
        if (cfg.EnabledFeatures.broadcast) {map.register(fallbackPrefix, new Broadcast(handler));}
        if (cfg.EnabledFeatures.CleanChat) {map.register(fallbackPrefix, new Cleanchat(handler));}
        if (cfg.EnabledFeatures.CleanInventory) {map.register(fallbackPrefix, new Cleaninventory(handler));}
        if (cfg.EnabledFeatures.CleanItem) {map.register(fallbackPrefix, new Cleanitem(handler));}
        if (cfg.EnabledFeatures.Day) {map.register(fallbackPrefix, new Day(handler));}
        if (cfg.EnabledFeatures.EnderChest) {map.register(fallbackPrefix, new Enderchest(handler));}
        if (cfg.EnabledFeatures.Feed) {map.register(fallbackPrefix, new Feed(handler));}
        if (cfg.EnabledFeatures.Fly) {map.register(fallbackPrefix, new Fly(handler));}
        if (cfg.EnabledFeatures.Gamemode) {map.register(fallbackPrefix, new Gamemode(handler));}
        if (cfg.EnabledFeatures.God) {map.register(fallbackPrefix, new God(handler));}
        if (cfg.EnabledFeatures.Heal) {map.register(fallbackPrefix, new Heal(handler));}
        if (cfg.EnabledFeatures.Invsee) {map.register(fallbackPrefix, new Invsee(handler));}
        if (cfg.EnabledFeatures.List) {map.register(fallbackPrefix, new List(handler));}
        if (cfg.EnabledFeatures.Night) {map.register(fallbackPrefix, new Night(handler));}
        if (cfg.EnabledFeatures.Rename) {map.register(fallbackPrefix, new Rename(handler));}
        if (cfg.EnabledFeatures.SetLore) {map.register(fallbackPrefix, new Setlore(handler));}
        if (cfg.EnabledFeatures.SetSpawn) {map.register(fallbackPrefix, new Setspawn(handler));}
        if (cfg.EnabledFeatures.Spawn) {map.register(fallbackPrefix, new Spawn(handler));}
        if (cfg.EnabledFeatures.Speed) {map.register(fallbackPrefix, new Speed(handler));}
        if (cfg.EnabledFeatures.Top) {map.register(fallbackPrefix, new Top(handler));}
        if (cfg.EnabledFeatures.Tpall) {map.register(fallbackPrefix, new Tpall(handler));}
        if (cfg.EnabledFeatures.Tphere) {map.register(fallbackPrefix, new Tphere(handler));}

        if (cfg.UseChatFormating) {pm.registerEvents(new ChatFormater(handler), this);}

        pm.registerEvents(new GodHandler(handler), this);
        pm.registerEvents(new SignColors(), this);
        pm.registerEvents(new StatusMessages(handler), this);
        pm.registerEvents(new SpawnHandler(handler), this);

    }
}
