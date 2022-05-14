package net.natroutter.minicore.utilities;

import net.natroutter.minicore.Handler;
import net.natroutter.minicore.files.Config;
import net.natroutter.minicore.objects.Particles;
import net.natroutter.minicore.objects.Sounds;
import net.natroutter.natlibs.handlers.ParticleSpawner;
import net.natroutter.natlibs.objects.ParticleSettings;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Effects {

    private Config config;
    private ParticleSpawner spawner;
    public Effects(Handler handler) {
        config = handler.getConfig();
        spawner = handler.getSpawner();
    }

    public void particle(Player p, Particles particle) {
        particle(p.getLocation(), particle);
    }
    public void particle(Location loc, Particles particle) {
        if (config.UseParticleEffects) {
            switch (particle) {
                case TeleportStart -> spawner.spawnParticleWorld(new ParticleSettings(config.Particles.TeleportStart, loc, 50, 0.5, 1, 0.5, 0));
                case TeleportEnd -> spawner.spawnParticleWorld(new ParticleSettings(config.Particles.TeleportEnd, loc, 50, 0.5, 1, 0.5, 0));
                case Fly -> spawner.spawnParticleWorld(new ParticleSettings(config.Particles.Fly, loc, 50, 0.5, 1, 0.5, 0));
                case Gamemode -> spawner.spawnParticleWorld(new ParticleSettings(config.Particles.Gamemode, loc, 50, 0.5, 1, 0.5, 0));
                case Heal -> spawner.spawnParticleWorld(new ParticleSettings(config.Particles.Heal, loc, 50, 0.5, 1, 0.5, 0));
                case Feed -> spawner.spawnParticleWorld(new ParticleSettings(config.Particles.Feed, loc, 50, 0.5, 1, 0.5, 0));
                case Speed -> spawner.spawnParticleWorld(new ParticleSettings(config.Particles.Speed, loc, 50, 0.5, 1, 0.5, 0));
                case Success -> spawner.spawnParticleWorld(new ParticleSettings(config.Particles.Success, loc, 50, 0.5, 1, 0.5, 0));
                case God -> spawner.spawnParticleWorld(new ParticleSettings(config.Particles.God, loc, 50, 0.5, 1, 0.5, 0));
            }
        }
    }

    public void sound(Player p, Sounds sounds) {
        if (config.UseSoundEffects) {
            Sound sound = getSound(sounds);
            float volume = getVolume(sounds);
            float pitch = getPitch(sounds);
            p.getWorld().playSound(p.getLocation(), sound, volume, pitch);
        }
    }

    private float getVolume(Sounds sounds) {
        switch (sounds) {
            case Modified -> { return config.Sounds.Modified_volume; }
            case Broadcast -> { return config.Sounds.Broadcast_volume; }
            case Teleported -> { return config.Sounds.Teleported_volume; }
            case Eat -> { return config.Sounds.Eat_volume; }
            case Fly -> { return config.Sounds.Fly_volume; }
            case Gamemode -> { return config.Sounds.Gamemode_volume; }
            case EnderChest -> { return config.Sounds.EnderChest_volume; }
            case Chest -> { return config.Sounds.Chest_volume; }
            case Heal -> { return config.Sounds.Heal_volume; }
            case SetSpawn -> { return config.Sounds.SetSpawn_volume; }
            case Speed -> { return config.Sounds.Speed_volume; }
            case God -> { return config.Sounds.God_volume; }
        }
        return 1;
    }

    private float getPitch(Sounds sounds) {
        switch (sounds) {
            case Modified -> { return config.Sounds.Modified_pitch; }
            case Broadcast -> { return config.Sounds.Broadcast_pitch; }
            case Teleported -> { return config.Sounds.Teleported_pitch; }
            case Eat -> { return config.Sounds.Eat_pitch; }
            case Fly -> { return config.Sounds.Fly_pitch; }
            case Gamemode -> { return config.Sounds.Gamemode_pitch; }
            case EnderChest -> { return config.Sounds.EnderChest_pitch; }
            case Chest -> { return config.Sounds.Chest_pitch; }
            case Heal -> { return config.Sounds.Heal_pitch; }
            case SetSpawn -> { return config.Sounds.SetSpawn_pitch; }
            case Speed -> { return config.Sounds.Speed_pitch; }
            case God -> { return config.Sounds.God_pitch; }
        }
        return 1;
    }

    private Sound getSound(Sounds sounds) {
        switch (sounds) {
            case Modified -> { return config.Sounds.Modified; }
            case Broadcast -> { return config.Sounds.Broadcast; }
            case Teleported -> { return config.Sounds.Teleported; }
            case Eat -> { return config.Sounds.Eat; }
            case Fly -> { return config.Sounds.Fly; }
            case Gamemode -> { return config.Sounds.Gamemode; }
            case EnderChest -> { return config.Sounds.EnderChest; }
            case Chest -> { return config.Sounds.Chest; }
            case Heal -> { return config.Sounds.Heal; }
            case SetSpawn -> { return config.Sounds.SetSpawn; }
            case Speed -> { return config.Sounds.Speed; }
            case God -> { return config.Sounds.God; }
        }
        return null;
    }

}
