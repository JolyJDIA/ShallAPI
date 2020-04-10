package jolyjdia.gadgets.gadgets.particle.particles;

import jolyjdia.gadgets.gadgets.particle.ParticleEffect;
import jolyjdia.utils.MathUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class VampireWings extends ParticleEffect {
    private static final boolean x = false;
    private static final boolean y = true;
    private static final boolean[][] SHAPE = {
            {x, x, x, x, y, x, x, x, x, x, x, x, y, x, x, x, x},
            {x, x, x, y, y, y, x, x, x, x, x, y, y, y, x, x, x},
            {x, x, y, y, y, y, y, x, y, x, y, y, y, y, y, x, x},
            {x, y, y, y, y, y, y, y, y, y, y, y, y, y, y, y, x},
            {x, y, y, y, y, y, y, y, y, y, y, y, y, y, y, y, x},
            {y, x, x, y, y, y, y, y, y, y, y, y, y, y, x, x, y},
            {x, x, x, x, y, y, y, y, y, y, y, y, y, x, x, x, x},
            {x, x, x, x, y, x, y, y, y, y, y, x, y, x, x, x, x},
            {x, x, x, y, x, x, x, y, x, y, x, x, x, y, x, x, x},
            {x, x, x, x, x, x, x, y, x, y, x, x, x, x, x, x, x}};

    //public static final Particle.DustOptions WHITE = new Particle.DustOptions(Color.fromBGR(255, 255, 255), 0.6f);
    //public static final Particle.DustOptions RED = new Particle.DustOptions(Color.fromBGR(0, 0, 255), 0.6f);
    //public static final Particle.DustOptions DARKRED = new Particle.DustOptions(Color.fromBGR(50, 50, 250), 0.3f);
    private static final float SPACE = 0.175F;
    private static final int length = SHAPE[0].length;
    private static final int half = length >> 1;//сдвиг (деление на 2)
    private static final double dex = half * SPACE;
    private final Particle.DustOptions dust = new Particle.DustOptions(
            Color.fromBGR(
                    MathUtils.randomDouble(0, 255),
                    MathUtils.randomDouble(0, 255),
                    MathUtils.randomDouble(0, 255)
            ), 0.7f);
    private double f = -0.5f;
    private boolean is = true;
    private double xPos;

    public VampireWings(Entity player) {
        super(player);
    }

    @Override
    public final void run() {
        Location loc = getLocation();
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
                    getWorld().spawnParticle(Particle.REDSTONE, loc, 0, dust);
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