package net.natroutter.minicore.files;

import org.bukkit.Particle;
import org.bukkit.Sound;

public class Config {

    public String language = "en_us";
    public String ChatFormat = "%prefix%§7%displayname%%suffix%§8: §f%message%";
    public Boolean UseChatFormating = true;
    public String BroadcastFormat = "§4§lBroadcast §8§l» §f%message%";
    public String DateFormat = "dd.MM.yyyy hh:mm";

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
    public Boolean DisableFlyOnJoin = false;


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

    public Particles Particles = new Particles();
    public class Particles {
        public Particle TeleportStart = Particle.PORTAL;
        public Particle TeleportEnd = Particle.PORTAL;
        public Particle Fly = Particle.CLOUD;
        public Particle Gamemode = Particle.DRAGON_BREATH;
        public Particle Heal = Particle.HEART;
        public Particle Feed = Particle.SMOKE_NORMAL;
        public Particle Speed = Particle.SOUL_FIRE_FLAME;
        public Particle Success = Particle.COMPOSTER;
        public Particle God = Particle.TOTEM;
    }

    public Sounds Sounds = new Sounds();
    public class Sounds {
        public Sound Modified = Sound.UI_LOOM_TAKE_RESULT;
        public float Modified_volume = 1f;
        public float Modified_pitch = 0.5f;

        public Sound Broadcast = Sound.BLOCK_NOTE_BLOCK_BELL;
        public float Broadcast_volume = 1f;
        public float Broadcast_pitch = 0.1f;

        public Sound Teleported = Sound.ITEM_CHORUS_FRUIT_TELEPORT;
        public float Teleported_volume = 1f;
        public float Teleported_pitch = 0.5f;

        public Sound Eat = Sound.ENTITY_FOX_EAT;
        public float Eat_volume = 1f;
        public float Eat_pitch = 0.5f;

        public Sound Fly = Sound.ENTITY_PARROT_FLY;
        public float Fly_volume = 1f;
        public float Fly_pitch = 1f;

        public Sound Gamemode = Sound.BLOCK_BEACON_ACTIVATE;
        public float Gamemode_volume = 1f;
        public float Gamemode_pitch = 1.5f;

        public Sound EnderChest = Sound.BLOCK_ENDER_CHEST_OPEN;
        public float EnderChest_volume = 1f;
        public float EnderChest_pitch = 1f;

        public Sound Chest = Sound.BLOCK_CHEST_OPEN;
        public float Chest_volume = 1f;
        public float Chest_pitch = 1f;

        public Sound Heal = Sound.ITEM_BOTTLE_FILL;
        public float Heal_volume = 1f;
        public float Heal_pitch = 1f;

        public Sound SetSpawn = Sound.BLOCK_RESPAWN_ANCHOR_SET_SPAWN;
        public float SetSpawn_volume = 1f;
        public float SetSpawn_pitch = 1f;

        public Sound Speed = Sound.ITEM_FIRECHARGE_USE;
        public float Speed_volume = 1f;
        public float Speed_pitch = 1f;

        public Sound God = Sound.BLOCK_BEACON_POWER_SELECT;
        public float God_volume = 1f;
        public float God_pitch = 2f;
    }

}
