package jolyjdia.gadgets.gadgets.particle.particles;

import jolyjdia.gadgets.gadgets.particle.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class DeathLess extends ParticleEffect {
    private static final double RADIUS = 0.17453292519943295;
    private float y = 0.1f;
    private int s;
    public DeathLess(Player player) {
        super(player);
    }

    @Override
    public final void run() {
        Location loc = getLocation().clone();
        double v = s * RADIUS;
        for(int i = 1; i < 3; ++i) {
            float angle = i == 1 ? -1.5f : 1.5f;
            double x = Math.cos(v) * angle;
            double z = Math.sin(v) * angle;
            getWorld().spawnParticle(Particle.END_ROD, loc, 0, x, y, z, 0.1F);
            getWorld().spawnParticle(Particle.SPELL_MOB, x,y,z, 0, color(30), color(255), color(255), 1);
        }
        y = y >= 1.0f ? 0 : (y += 0.1f);
        ++s;
    }
    private static float color(float x) {
        x = x <= 0 ? -1 : x;
        return x / 255;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
