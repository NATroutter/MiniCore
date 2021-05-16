package net.natroutter.minicore;

import net.natroutter.minicore.commands.*;
import net.natroutter.minicore.handlers.ChatFormater;
import net.natroutter.minicore.handlers.Database.Database;
import net.natroutter.minicore.handlers.Hooks;
import net.natroutter.minicore.handlers.features.GodHandler;
import net.natroutter.minicore.utilities.Config;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.natlibs.NATLibs;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.handlers.EventManager;
import net.natroutter.natlibs.handlers.FileManager;
import net.natroutter.natlibs.objects.CondCommand;
import net.natroutter.natlibs.objects.ConfType;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.plugin.java.JavaPlugin;

public class MiniCore extends JavaPlugin {

    /*
        TODO
        Add commands:
            god, show, msg, reply, socialspy

        Particelit ja äänet komentoihin config disable/enable
        Tab complete kaikkiin komentoihin!
        SignColor
        join/quit messages custom and disable config
        spawnaus spawnille kuollessa sänky mahollisuus
    */

    private static Hooks hooks;
    private static Config config;
    private static Lang lang;
    private static Database database;
    private static YamlDatabase yamlDatabase;
    private static Utilities utilities;

    public static Hooks getHooks(){return hooks;}
    public static Config getConf(){return config;}
    public static Lang getLang(){return lang;}
    public static Database getDatabase(){return database;}
    public static YamlDatabase getYamlDatabase(){return yamlDatabase;}
    public static Utilities getUtilities(){return utilities;}

    @Override
    public void onEnable() {
        NATLibs lib = new NATLibs(this);

        hooks = new Hooks(this);
        config = new FileManager(this, ConfType.Config).load(Config.class);
        lang = new FileManager(this, ConfType.Lang).load(Lang.class);
        database = new Database(this);
        yamlDatabase = new YamlDatabase(this);
        utilities = new Utilities(this);

        EventManager evm = new EventManager(this);
        evm.RegisterConditionalCommands(
                new CondCommand(Addlore.class, config.EnabledFeatures.Addlore),
                new CondCommand(Broadcast.class, config.EnabledFeatures.broadcast),
                new CondCommand(Cleanchat.class, config.EnabledFeatures.CleanChat),
                new CondCommand(Cleaninventory.class, config.EnabledFeatures.CleanInventory),
                new CondCommand(Cleanitem.class, config.EnabledFeatures.CleanItem),
                new CondCommand(Day.class, config.EnabledFeatures.Day),
                new CondCommand(Enderchest.class, config.EnabledFeatures.EnderChest),
                new CondCommand(Feed.class, config.EnabledFeatures.Feed),
                new CondCommand(Fly.class, config.EnabledFeatures.Fly),
                new CondCommand(Gamemode.class, config.EnabledFeatures.Gamemode),
                new CondCommand(Heal.class, config.EnabledFeatures.Heal),
                new CondCommand(Invsee.class, config.EnabledFeatures.Invsee),
                new CondCommand(List.class, config.EnabledFeatures.List),
                new CondCommand(Night.class, config.EnabledFeatures.Night),
                new CondCommand(Rename.class, config.EnabledFeatures.Rename),
                new CondCommand(Setlore.class, config.EnabledFeatures.SetLore),
                new CondCommand(Setspawn.class, config.EnabledFeatures.SetSpawn),
                new CondCommand(Spawn.class, config.EnabledFeatures.Spawn),
                new CondCommand(Speed.class, config.EnabledFeatures.Speed),
                new CondCommand(Top.class, config.EnabledFeatures.Top),
                new CondCommand(Tpall.class, config.EnabledFeatures.Tpall),
                new CondCommand(Tphere.class, config.EnabledFeatures.Tphere)
        );
        evm.RegisterListeners(ChatFormater.class, GodHandler.class);

    }
}
