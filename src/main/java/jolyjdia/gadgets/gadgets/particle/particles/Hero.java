package jolyjdia.gadgets.gadgets.particle.particles;

import jolyjdia.gadgets.gadgets.particle.ParticleEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class Hero extends ParticleEffect {
    public static final Particle.DustOptions COLORED_DUST = new Particle.DustOptions(Color.fromBGR(0, 0, 255), 0.5f);
    public Hero(Entity player) {
        super(player);
    }

    @Override
    public final void run() {
        Location loc = getLocation();
        Vector v = loc.toVector();
        getWorld().spawnParticle(Particle.CLOUD, loc.add(0,-0.1,0), 0);
        double defX = loc.getX() - 0.4F;
        double x = defX;
        double defT = loc.getY() + 1.5F;
        double angle = -Math.toRadians(loc.getYaw());//если джава 13 и выше, в противном случае лучше юзать MathUtils.toRadians
        Location target = loc.clone();
        for (int i = 0; i < 8; ++i) {
            target.setY(defT);
            for (int j = 0; j < 5; ++j) {
                target.setX(x);

                Vector aroundY = target.toVector().subtract(v).rotateAroundY(angle);
                loc.add(aroundY);
               // if (getPlayer().isSprinting()) {
               //     loc.setY(defY);
             //   }
                for (int k = 0; k < 3; ++k) {
                    getWorld().spawnParticle(Particle.REDSTONE, loc, 0, COLORED_DUST);
                }
                loc.subtract(aroundY);
                x += 0.2;
            }
            defT -= 0.2;
            x = defX;
        }
    }

    @Override
    public void start() {}

    @Override
    public void stop() {}
}
