package jolyjdia.gadgets.gadgets.particle.particles;

import jolyjdia.gadgets.gadgets.particle.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Titan extends ParticleEffect {
    Vector vector = new Vector();
    Vector v = new Vector();
    protected Titan(Player player) {
        super(player);
    }
    //если джава 13 и выше, в противном случае лучше юзать MathUtils.toRadians
    @Override
    public final void run() {
        Location loc = getLocation();
        for(int i = 1; i < 4; ++i) {
            float i1 = (120 * i);
            double x = Math.cos(Math.toRadians(i1)) * 3.5f;
            vector.setX(x);
            vector.setY(0.1f);
            double z = Math.sin(Math.toRadians(i1)) * 3.5f;
            vector.setZ(z);
            vector.rotateAroundY(Math.toRadians(i*Math.PI/46));
            loc.add(vector);
            getWorld().spawnParticle(Particle.FLAME, loc, 0);
            loc.subtract(vector);
            v.setX(x);
            v.setY(0.1f);
            v.setZ(z);
            v.rotateAroundY(-Math.toRadians(i * Math.PI / 46.0f));
            loc.add(v);
            getWorld().spawnParticle(Particle.FLAME, loc, 0);
            loc.subtract(v);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
