package net.natroutter.minicore.utilities;

import java.util.ArrayList;
import java.util.List;

public class Lang {

    public String Prefix = "§4§lMiniCore §8§l» ";
    public String NoPerm = "§7You do not have permission to do this";
    public String InvalidArgs = "§7Invalid command arguments";
    public String ToomanyArgs = "§7Too many command arguments";
    public String OnlyIngame = "§7This command can only be executed ingame!";
    public String ToggleFly = "§7Flying: §c{state}";
    public String ToggleFlyOther = "§c{player}§7's flying: §c{state}";
    public String SpeedNotDefined = "§7Speed not defined!";
    public String InvalidSpeed = "§7That is not valid speed!";
    public String InvalidSpeedType = "§7Invalid speedtype valids: Walking,Flying";
    public String SpeedOutOfRange = "§7Speed must be between §c1-10";
    public String SpeedChanged = "§7Your §c{type} §7speed is now §c{speed}";
    public String SpeedChangedOther = "§7Changed §c{player}§7's §c{type} §7speed to §c{speed}";
    public String InvalidPlayer = "§7Invalid Player!";
    public String InvalidGamemode = "§7That is not a valid gamemode";
    public String GamemodeChanged = "§7Your gamemode has been changed to §c{gamemode}";
    public String GamemodeChangedOther = "§c{player}§7' gamemode has been changed to §c{gamemode}";
    public String ItemRenamed = "§7Item has been renamed!";
    public String InvalidItem = "§7You are not holding an item!";
    public String LoreChanged = "§7Items lore has been changed!";
    public String EnderChestOpened ="§7Enderchest opened";
    public String EnderChestOpenedOther ="§c{player}§7's enderchest opened!";
    public String PlayerInvOpened = "§c{player}§7's inventory opened!";
    public String SpawnSet = "§7Spawn has been set to your location!";
    public String SpawnNotset = "§7Spawn has not been set!";
    public String TeleportedToSpawn = "§7You have been teleported to spawn!";
    public String ItemCleaned = "§7Item has been cleaned";
    public String ItemAlreadyClean = "§7Item is already clean!";
    public String LoreAdded = "§7Lore added to item!";
    public String InvalidBroadcastMessage = "§7you need to define broadcast message";
    public String ChatCleaned = "§7Chat cleaned by: §c{player}";
    public String inventoryCleaned = "§7Inventory Cleaned!";
    public String inventoryCleanedOther = "§c{player} §7inventory Cleaned!";
    public String TimeSetToDay = "§7Time set to §cDay";
    public String TimeSetToNight = "§7Time set to §cNight";
    public String Fed = "§7You have been fed";
    public String FedOther = "§c{player} §7has been fed!";
    public String Healed = "§7You have been healed";
    public String HealedOther = "§c{player} §7has been healed";
    public String TeleportedToTop = "§7You have been teleported to top";
    public String TeleportedToYou = "§c{player} §7Teleported to you";
    public String AllPlayersTeleported = "§7All players teleported to you";
    public String CantTargetYourSelf = "§7Target can't be you!";
    public String GodToggled = "§7Godmode §c{status}";
    public String GodToggledOther = "§c{player}§7's Godmode §c{status}";
    public String Unknown = "Unknown";
    public String Joined = "§c{player}§7 joined the server";
    public String Quit = "§c{player}§7 left the server";
    public String FirstTimeWelcome = "§c{player}§7 is a new player!";

    public String LocationFormat = "§8(§c{world}§7, §c{x}§7, §c{y}§7, §c{z}§7, §c{pitch}§7, §c{yaw}§8)";

    public List<String> ShowFormat = new ArrayList<>() {{
        add("§c§l-------+ §4§lMiniCore §c§l+-------");
        add("§7UUID§8: §c{uuid}");
        add("§7Name§8: §c{name}");
        add("§7DisplayName§8: §c{displayname}");
        add("§7Online§8: §c{online}");
        add("§7Ip Address§8: §c{ip}");
        add("§7Last Location§8: §c{lastloc}");
        add("§7Locale§8: §c{locale}");
        add("§7Health§8: §c{health}§8/§c{maxhealth}");
        add("§7Food§8: §c{food}§8/§c{maxfood}");
        add("§7Level§8: §c{level}");
        add("§7Gamemode§8: §c{gamemode}");
        add("§7Flying speed§8: §c{flyspeed}");
        add("§7Walking speed§8: §c{walkspeed}");
        add("§7World§8: §c{world}");
        add("§7First Played§8: §c{firstplayed}");
        add("§7Last Played§8: §c{lastplayed}");
        add("§7Bed Location§8: §c{bedlocation}");
        add("§7Flying§8: §c{flying}");
        add("§7Allowed Fly§8: §c{allowfly}");
        add("§7Inside Vehicle§8: §c{invehicle}");
        add("§7God§8: §c{god}");
        add("§c§l-------+ §4§lMiniCore §c§l+-------");
    }};

    public ListFormat ListFormat = new ListFormat();
    public static class ListFormat {
        public List<String> Format = new ArrayList<>() {{
            add("§c§l-------+ §4§lMiniCore §c§l+-------");
            add("{entries}");
            add("");
            add("§7Total: §c{online}§8/§c{max}");
            add("§c§l-------+ §4§lMiniCore §c§l+-------");
        }};
        public String Entry = "§c{displayname}§7";
        public String Separator = ", ";
    }

    public GameModes GameModes = new GameModes();
    public static class GameModes {
        public String Survival = "Survival";
        public String Creative = "Creative";
        public String Adventure = "Adventure";
        public String Spectator = "Spectator";
    }

    public ToggleStates ToggleStates = new ToggleStates();
    public static class ToggleStates {
        public String enabled = "Enabled";
        public String disabled = "Disabled";
    }

    public SpeedTypes SpeedTypes = new SpeedTypes();
    public static class SpeedTypes {
        public String Walking = "Walking";
        public String Flying = "Flying";
    }

}
