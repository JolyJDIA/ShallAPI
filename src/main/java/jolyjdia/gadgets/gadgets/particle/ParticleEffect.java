package jolyjdia.gadgets.gadgets.particle;

import jolyjdia.gadgets.gadgets.Gadget;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

public abstract class ParticleEffect extends Gadget {
    private Particle particle;
    protected ParticleEffect(Entity player, boolean async) {
        super(player, async);
    }
    protected ParticleEffect(Entity player) {
        super(player);
    }

    public final Particle getParticle() {
        return particle;
    }

    public final void setParticle(Particle particle) {
        this.particle = particle;
    }

}
