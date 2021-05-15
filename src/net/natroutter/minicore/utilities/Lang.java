package net.natroutter.minicore.utilities;

public class Lang {

    public String Prefix = "§4§lMiniCore §8§l» ";
    public String IngameOnly = "§7This command can only be run ingame!";
    public String NoPerm = "§7You do not have permission to do this";
    public String InvalidArgs = "§7Invalid command arguments";
    public String ToomanyArgs = "§7Too many command arguments";
    public String OnlyIngame = "§7This Command can only be executed ingame!";
    public String ToggleFly = "§7Flying: §c{state}";
    public String ToggleFlyOther = "§c{player}§7's Flying: §c{state}";
    public String SpeedNotDefined = "§7Speed not defined!";
    public String InvalidSpeed = "§7That is not valid speed!";
    public String InvalidSpeedType = "§7Invalid SpeedType Valids: Walking,Flying";
    public String SpeedOutOfRange = "§7Speed must be between §c1-10";
    public String SpeedChanged = "§7Your §c{type} §7Speed is now §c{speed}";
    public String SpeedChangedOther = "§7Changed §c{player}§7's §c{type} §7Speed to §c{speed}";
    public String InvalidPlayer = "§7Invalid Player!";
    public String InvalidGamemode = "§7That is not a valid gamemode";
    public String GamemodeChanged = "§7Your gamemode has been changed to §c{gamemode}";
    public String GamemodeChangedOther = "§c{player}§7' Gamemode has been changed to §c{gamemode}";
    public String ItemRenamed = "§7Item has been renamed!";
    public String InvalidItem = "§7You are not holding an item!";
    public String LoreChanged = "§7Items lore has been changed!";
    public String EnderChestOpened ="§7Enderchest opened";
    public String EnderChestOpenedOther ="§c{Player}§7's enderchest opened!";
    public String PlayerInvOpened = "§7Inventory opened!";
    public String SpawnSet = "§7Spawn has been set to your location!";
    public String SpawnNotset = "§7Spawn has not been set!";
    public String TeleportedToSpawn = "§7You have been teleported to spawn!";


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
