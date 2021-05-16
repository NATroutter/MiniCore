package net.natroutter.minicore.objects;

import org.bukkit.Sound;

public class SoundSettings {

    private Sound sound;
    private Float volume;
    private Float pitch;

    public SoundSettings(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public Sound getSound() {
        return sound;
    }

    public Float getVolume() {
        return volume;
    }

    public Float getPitch() {
        return pitch;
    }
}
