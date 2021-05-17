package net.natroutter.minicore.utilities;

public class Config {

    public String ChatFormat = "{prefix}§7{displayname}{suffix}§8: §f{message}";
    public String BroadcastFormat = "§4§lBroadcast §8§l» §f{message}";
    public String DateFormat = "MM.dd.yyyy hh:mm";

    public Boolean UseParticleEffects = true;
    public Boolean UseSoundEffects = true;
    public Boolean DisableJoinMessage = false;
    public Boolean UseCustomJoinMessage = true;
    public Boolean DisableQuitMessage = false;
    public Boolean WelcomeMessages = true;
    public Boolean ShowJoinMessageIfNewPlayer = true;
    public Boolean AllowBedSpawn = true;
    public Boolean ForceSpawnOnJoin = true;
    public Boolean DisableGodOnJoin = true;


    public EnabledFeatures EnabledFeatures = new EnabledFeatures();
    public static class EnabledFeatures {
        public Boolean Addlore = true;
        public Boolean broadcast = true;
        public Boolean CleanChat = true;
        public Boolean CleanInventory = true;
        public Boolean CleanItem = true;
        public Boolean Day = true;
        public Boolean EnderChest = true;
        public Boolean Feed = true;
        public Boolean Fly = true;
        public Boolean Gamemode = true;
        public Boolean God = true;
        public Boolean Heal = true;
        public Boolean Invsee = true;
        public Boolean List = true;
        public Boolean Night = true;
        public Boolean Rename = true;
        public Boolean SetLore = true;
        public Boolean SetSpawn = true;
        public Boolean Show = true;
        public Boolean Spawn = true;
        public Boolean Speed = true;
        public Boolean Top = true;
        public Boolean Tpall = true;
        public Boolean Tphere = true;

    }

}
