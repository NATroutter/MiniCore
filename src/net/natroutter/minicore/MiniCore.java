package net.natroutter.minicore;

import net.natroutter.minicore.commands.*;
import net.natroutter.minicore.handlers.Database.handlers.PlayerDataHandler;
import net.natroutter.minicore.handlers.features.*;
import net.natroutter.minicore.handlers.Database.Database;
import net.natroutter.minicore.handlers.Hooks;
import net.natroutter.minicore.utilities.Config;
import net.natroutter.minicore.utilities.Lang;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.NATLibs;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.handlers.EventManager;
import net.natroutter.natlibs.handlers.FileManager;
import net.natroutter.natlibs.objects.CondCommand;
import net.natroutter.natlibs.objects.ConfType;
import net.natroutter.natlibs.utilities.MojangAPI;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.plugin.java.JavaPlugin;

public class MiniCore extends JavaPlugin implements NATLibs {

    /*
        TODO
        Add commands:
            msg, reply, socialspy, homes tpa tpahere, tpaccept, tpdeny, signedit

        Add cooldowns for some commands with configs
        move check for teleporting

    */

    private static Hooks hooks;
    private static Config config;
    private static Lang lang;
    private static YamlDatabase yamlDatabase;
    private static Utilities utilities;
    private static MojangAPI mojangAPI;
    private static PlayerDataHandler playerDataHandler;

    public static Hooks getHooks(){return hooks;}
    public static Config getConf(){return config;}
    public static Lang getLang(){return lang;}
    public static YamlDatabase getYamlDatabase(){return yamlDatabase;}
    public static Utilities getUtilities(){return utilities;}
    public static MojangAPI getMojangAPI(){return mojangAPI;}
    public static PlayerDataHandler getDataHandler(){return playerDataHandler;}

    @Override
    public void onEnable() {
        registerLibrary(this);

        hooks = new Hooks(this);
        config = new FileManager(this, ConfType.Config).load(Config.class);
        lang = new FileManager(this, ConfType.Lang).load(Lang.class);
        playerDataHandler = new PlayerDataHandler(this, new Database(this), 30 * 60);
        yamlDatabase = new YamlDatabase(this);
        utilities = new Utilities(this);
        mojangAPI = new MojangAPI(this);

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
                new CondCommand(God.class, config.EnabledFeatures.God),
                new CondCommand(Heal.class, config.EnabledFeatures.Heal),
                new CondCommand(Invsee.class, config.EnabledFeatures.Invsee),
                new CondCommand(List.class, config.EnabledFeatures.List),
                new CondCommand(Night.class, config.EnabledFeatures.Night),
                new CondCommand(Rename.class, config.EnabledFeatures.Rename),
                new CondCommand(Setlore.class, config.EnabledFeatures.SetLore),
                new CondCommand(Setspawn.class, config.EnabledFeatures.SetSpawn),
                new CondCommand(Show.class, config.EnabledFeatures.Show),
                new CondCommand(Spawn.class, config.EnabledFeatures.Spawn),
                new CondCommand(Speed.class, config.EnabledFeatures.Speed),
                new CondCommand(Top.class, config.EnabledFeatures.Top),
                new CondCommand(Tpall.class, config.EnabledFeatures.Tpall),
                new CondCommand(Tphere.class, config.EnabledFeatures.Tphere)
        );
        if (config.UseChatFormating) {
            evm.RegisterListeners(ChatFormater.class); //TODO replace this with conditional system like above!!!
        }
        evm.RegisterListeners(
                GodHandler.class,
                InfoHandler.class,
                SignColors.class,
                StatusMessages.class,
                SpawnHandler.class
        );

        Utils.FancyPluginMessage(this);

    }
}
