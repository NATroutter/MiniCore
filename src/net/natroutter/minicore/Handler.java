package net.natroutter.minicore;

import lombok.Getter;
import net.natroutter.minicore.handlers.Hooks;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.utilities.Effects;
import net.natroutter.minicore.utilities.Utils;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.handlers.LangHandler.language.LangManager;
import net.natroutter.natlibs.handlers.LangHandler.language.Language;
import net.natroutter.natlibs.handlers.LangHandler.language.key.LanguageKey;
import net.natroutter.natlibs.handlers.ParticleSpawner;
import net.natroutter.natlibs.handlers.configuration.ConfigManager;
import net.natroutter.natlibs.utilities.MojangAPI;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

@Getter
public class Handler {

    private JavaPlugin instance;
    private Hooks hooks;
    private Config config;
    private LangManager lang;
    private YamlDatabase yamlDatabase;
    private Utilities utilities;
    private MojangAPI mojangAPI;
    private Utils utils;
    private ParticleSpawner spawner;
    private Effects effects;

    public Handler(JavaPlugin instance) {
        this.instance = instance;

        config = new ConfigManager(instance).load(Config.class);

        ConsoleCommandSender console = Bukkit.getConsoleSender();
        PluginDescriptionFile pdf = instance.getDescription();

        Optional<Language> language = Language.getFromKey(LanguageKey.of(config.language));
        this.lang = language.map(value -> new LangManager(instance, value)).orElseGet(() -> {
            console.sendMessage("§4["+instance.getName()+"][Lang] §cLanguage file defined in config not found, Using default!");
            return new LangManager(instance, Language.ENGLISH);
        });

        console.sendMessage("§5                                                  ");
        console.sendMessage("§5      __  __ _      _  ___                        ");
        console.sendMessage("§5     |  \\/  (_)_ _ (_)/ __|___ _ _ ___            ");
        console.sendMessage("§5     | |\\/| | | ' \\| | (__/ _ \\ '_/ -_)           ");
        console.sendMessage("§5     |_|  |_|_|_||_|_|\\___\\___/_| \\___|           ");
        console.sendMessage("§5                                                  ");
        console.sendMessage("§5            Loading version §d"+pdf.getVersion());
        console.sendMessage("§5     Minicore made with §d❤ §5by §dNATroutter         ");
        console.sendMessage("§5	                                                 ");
        hooks = new Hooks(instance);
        console.sendMessage("§5	                                                 ");

        spawner = new ParticleSpawner();
        effects = new Effects(this);

        utils = new Utils(this);
        yamlDatabase = new YamlDatabase(instance);
        utilities = new Utilities(instance);
        mojangAPI = new MojangAPI(instance);



    }

}
