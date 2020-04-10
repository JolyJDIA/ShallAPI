package jolyjdia.gadgets.gadgets.particle.particles;

import jolyjdia.gadgets.gadgets.particle.ParticleEffect;
import jolyjdia.utils.MathUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class BloodHelix extends ParticleEffect {
    private static final double RADIUS = 0.19634954084936207;
    private static final Particle.DustOptions COLORED_DUST = new Particle.DustOptions(Color.fromBGR(0, 0, 255), 1);
    private float phi;
    public BloodHelix(Player player) {
        super(player);
    }

    @Override

    public final void run() {
        this.phi += RADIUS;
        Location loc = getPlayer().getLocation();
        for(double t = 0; t <= MathUtils.CIRCE; t += RADIUS) {
            double angle = t + phi;
            double grow = 0.25*(MathUtils.CIRCE-t);
            double y = 0.5*t;
            for(int i = 0; i < 2; ++i) {
                angle += i*Math.PI;
                double x = grow*Math.cos(angle);
                double z = grow*Math.sin(angle);
                loc.add(x,y,z);
                getPlayer().getWorld().spawnParticle(Particle.REDSTONE, loc, 1, COLORED_DUST);
                loc.subtract(x,y,z);
            }
        }

    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
}
