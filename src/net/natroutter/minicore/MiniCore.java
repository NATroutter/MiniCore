package net.natroutter.minicore;

import net.natroutter.minicore.commands.*;
import net.natroutter.minicore.handlers.ChatFormater;
import net.natroutter.minicore.handlers.Database.Database;
import net.natroutter.minicore.handlers.Hooks;
import net.natroutter.minicore.utilities.Config;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.natlibs.NATLibs;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.handlers.EventManager;
import net.natroutter.natlibs.handlers.FileManager;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.plugin.java.JavaPlugin;

public class MiniCore extends JavaPlugin {

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
        config = new FileManager(this, FileManager.ConfType.Config).load(Config.class);
        lang = new FileManager(this, FileManager.ConfType.Lang).load(Lang.class);
        database = new Database(this);
        yamlDatabase = new YamlDatabase(this);
        utilities = new Utilities(this);

        EventManager evm = new EventManager(this);
        evm.RegisterCommands(
                Enderchest.class,
                Fly.class,
                Gamemode.class,
                Invsee.class,
                Rename.class,
                Setlore.class,
                Setspawn.class,
                Spawn.class,
                Speed.class
        );
        evm.RegisterListeners(ChatFormater.class);

    }
}
