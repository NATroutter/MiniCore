package net.natroutter.minicore.utilities;

import net.natroutter.minicore.MiniCore;
import net.natroutter.minicore.objects.SoundSettings;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.objects.ParticleSettings;
import net.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;

public class Effect {

    private static final Utilities utils = MiniCore.getUtilities();
    private static final Config config = MiniCore.getConf();
    private static final Integer radius = 50;

    public static void particle(ParticleSettings set) {
        if (config.UseParticleEffects) {
            utils.spawnParticleInRadius(set, radius);
        }
    }

    public static void sound(BasePlayer p, SoundSettings set) {
        if (config.UseSoundEffects) {
            p.playSound(p.getLocation(), set.getSound(), SoundCategory.MASTER ,set.getVolume() ,set.getPitch());
        }
    }




}
