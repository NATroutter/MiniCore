package net.natroutter.minicore.utilities;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.files.Translations;
import net.natroutter.natlibs.handlers.ParticleSpawner;
import net.natroutter.natlibs.handlers.langHandler.language.LangManager;
import net.natroutter.natlibs.objects.ParticleSettings;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private LangManager lang;

    public Utils(Handler handler) {
        lang = handler.getLang();
    }

    public List<String> playerNameList() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
    }
    public List<String> worldNameList() {
        return Bukkit.getWorlds().stream().map(World::getName).toList();
    }

    public GameMode ValidateGamemode(String gm) {
        return switch (gm.toLowerCase()) {
            case "s", "0", "survival" -> GameMode.SURVIVAL;
            case "c", "1", "creative" -> GameMode.CREATIVE;
            case "a", "2", "adventure" -> GameMode.ADVENTURE;
            case "sp", "3", "spectator" -> GameMode.SPECTATOR;
            default -> null;
        };
    }

    public String getGamemodeName(GameMode gm) {
        return switch (gm) {
            case SURVIVAL -> lang.get(Translations.GameModes_Survival);
            case CREATIVE -> lang.get(Translations.GameModes_Creative);
            case ADVENTURE -> lang.get(Translations.GameModes_Adventure);
            case SPECTATOR -> lang.get(Translations.GameModes_Separator);
        };
    }
}
