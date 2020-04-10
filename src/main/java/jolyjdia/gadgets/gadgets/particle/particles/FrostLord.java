package jolyjdia.gadgets.gadgets.particle.particles;

import jolyjdia.gadgets.gadgets.particle.ParticleEffect;
import jolyjdia.utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class FrostLord extends ParticleEffect {
    private float t;
    public FrostLord(Player player) {
        super(player);
    }
    @Override
    public final void run() {
        World w = getPlayer().getWorld();
        Location loc = getLocation();
        float xpi = 0.3F * (12.56F - t);
        float y = 0.25F * t;
        for (float phi = 0F; phi <= MathUtils.CIRCE; phi += 1.57F) {
            float angle = t + phi;
            double x = xpi * Math.cos(angle);
            double z = xpi * Math.sin(angle);
            loc.add(x, y, z);
            w.spawnParticle(Particle.SNOW_SHOVEL, loc, 0);
            loc.subtract(x, y, z);
        }
        if (t > 12.56F) {
            w.spawnParticle(Particle.SNOW_SHOVEL, loc.clone().add(0, 3.5F, 0), 50);
            w.playSound(loc, Sound.BLOCK_SNOW_BREAK, 1.0F, 1.0F);
            this.t = 0;
        }
        t += 0.4;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
