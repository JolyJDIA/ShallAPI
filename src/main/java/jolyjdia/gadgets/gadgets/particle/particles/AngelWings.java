package jolyjdia.gadgets.gadgets.particle.particles;

import jolyjdia.gadgets.gadgets.particle.ParticleEffect;
import jolyjdia.utils.MathUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class AngelWings extends ParticleEffect {
    private static final boolean x = false;
    private static final boolean y = true;
    private static final boolean[][] SHAPE = {
            {x, x, x, x, y, x, x, x, x, x, y, x, x, x, x},
            {x, x, y, y, y, y, x, x, x, y, y, y, y, x, x},
            {x, y, y, y, y, x, x, x, x, x, y, y, y, y, x},
            {x, y, y, y, y, x, x, x, x, x, y, y, y, y, x},
            {y, y, y, y, y, y, x, x, x, y, y, y, y, y, y},
            {y, y, y, y, x, x, x, x, x, x, x, y, y, y, y},
            {y, y, y, x, x, x, x, x, x, x, x, x, y, y, y},
            {x, y, y, x, x, x, x, x, x, x, x, x, y, y, x},
            {x, x, y, x, x, x, x, x, x, x, x, x, y, x, x},
            {x, x, y, y, x, x, x, x, x, x, x, y, y, x, x},
            {x, x, x, y, x, x, x, x, x, x, x, y, x, x, x}
    };
    public static final Particle.DustOptions COLORED_HALO = new Particle.DustOptions(Color.fromBGR(5,250,250),0.5f);
    public static final Particle.DustOptions COLORED_DUST = new Particle.DustOptions(Color.fromBGR(225,225,225),0.8f);
    private static final float SPACE = 0.175F;
    private static final int length = SHAPE[0].length;
    private static final int half = length >> 1;//сдвиг (деление на 2)
    private static final double dex = half * SPACE;
    private double f = -0.5f;
    private boolean is = y;
    private double xPos;

    public AngelWings(Entity player) {
        super(player);
    }

    @Override
    public final void run() {
        Location loc = getLocation();

        for(float i = 0; i <= MathUtils.CIRCE; i += 0.3F) {
            double x1 = Math.cos(i) * 0.4F;
            double z1 = Math.sin(i) * 0.4F;
            loc.add(x1, 2.4, z1);
            getWorld().spawnParticle(Particle.REDSTONE, loc, 0, COLORED_HALO);
            loc.subtract(x1, 2.4, z1);
        }
        Vector start = loc.toVector();
        double defX = loc.getX() - dex;

        double yPos = loc.getY() + 2;
        double angle = Math.toRadians(loc.getYaw());//если джава 13 и выше, в противном случае лучше юзать MathUtils.toRadians
        Location target = loc.clone();
        for (boolean[] aShape : SHAPE) {
            target.setY(yPos);
            for (int i = 0; i < length; ++i) {
                target.setX(xPos);

                if (aShape[i]) {
                    Vector aroundY = target.toVector().subtract(start).rotateAroundY(i < half ? f - angle : -f - angle);
                    loc.add(aroundY);
                    getWorld().spawnParticle(Particle.REDSTONE, loc, 0, COLORED_DUST);
                    loc.subtract(aroundY);
                }
                this.xPos += SPACE;
            }
            yPos -= SPACE;
            this.xPos = defX;
        }
        this.is = is ? (((f -= 0.12F)) > -1.3f) : (((f += 0.1F)) > -0.4f);
    }

    @Override
    public void start() {}

    @Override
    public void stop() {}
}