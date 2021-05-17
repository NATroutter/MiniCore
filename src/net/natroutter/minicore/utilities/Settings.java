package net.natroutter.minicore.utilities;

import net.natroutter.minicore.objects.SoundSettings;
import net.natroutter.natlibs.objects.ParticleSettings;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class Settings {

    public static class Particle {
        public static ParticleSettings base(org.bukkit.Particle particle, Location loc) {
            return new ParticleSettings(particle, loc, 50, 0.5, 1, 0.5, 0);
        }

        public static ParticleSettings teleportStart(Location loc) {
            return base(org.bukkit.Particle.PORTAL, loc);
        }

        public static ParticleSettings teleportEnd(Location loc) {
            return base(org.bukkit.Particle.PORTAL, loc);
        }

        public static ParticleSettings fly(Location loc) {
            return base(org.bukkit.Particle.CLOUD, loc);
        }

        public static ParticleSettings gamemode(Location loc) {
            return base(org.bukkit.Particle.DRAGON_BREATH, loc);
        }

        public static ParticleSettings heal(Location loc) {
            return base(org.bukkit.Particle.HEART, loc);
        }
        public static ParticleSettings feed(Location loc) {
            return base(org.bukkit.Particle.SMOKE_NORMAL, loc);
        }
        public static ParticleSettings speed(Location loc) {
            return base(org.bukkit.Particle.SOUL_FIRE_FLAME, loc);
        }
        public static ParticleSettings Success(Location loc) {
            return base(org.bukkit.Particle.COMPOSTER, loc);
        }
        public static ParticleSettings God(Location loc) {
            return base(org.bukkit.Particle.TOTEM, loc);
        }
    }

    public static class Sound {
        public static SoundSettings modified() {
            return new SoundSettings(org.bukkit.Sound.UI_LOOM_TAKE_RESULT, 100, 0.5f);
        }

        public static SoundSettings broadcast() {
            return new SoundSettings(org.bukkit.Sound.BLOCK_NOTE_BLOCK_BELL, 100, 0.1f);
        }

        public static SoundSettings teleported() {
            return new SoundSettings(org.bukkit.Sound.ITEM_CHORUS_FRUIT_TELEPORT, 100, 0.5f);
        }

        public static SoundSettings eat() {
            return new SoundSettings(org.bukkit.Sound.ENTITY_FOX_EAT, 100, 0.5f);
        }
        public static SoundSettings fly() {
            return new SoundSettings(org.bukkit.Sound.ENTITY_PARROT_FLY, 100, 1f);
        }
        public static SoundSettings gamemode() {
            return new SoundSettings(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE, 100, 1.5f);
        }
        public static SoundSettings enderChest() {
            return new SoundSettings(org.bukkit.Sound.BLOCK_ENDER_CHEST_OPEN, 100, 1f);
        }
        public static SoundSettings chest() {
            return new SoundSettings(org.bukkit.Sound.BLOCK_CHEST_OPEN, 100, 1f);
        }
        public static SoundSettings heal() {
            return new SoundSettings(org.bukkit.Sound.ITEM_BOTTLE_FILL, 100, 1f);
        }
        public static SoundSettings setspawn() {return new SoundSettings(org.bukkit.Sound.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, 100, 1f); }
        public static SoundSettings speed() {return new SoundSettings(org.bukkit.Sound.ITEM_FIRECHARGE_USE, 100, 1f); }
        public static SoundSettings god() {return new SoundSettings(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT, 100, 2f); }
    }

}
