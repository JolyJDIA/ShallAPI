package jolyjdia.nms.v1_15_R1.entity;

import jolyjdia.nms.interfaces.entity.DEntityZombie;
import net.minecraft.server.v1_15_R1.EntityZombie;
import net.minecraft.server.v1_15_R1.World;

public class DEntityZombieImpl extends DEntityLivingBase<EntityZombie> implements DEntityZombie {

    public DEntityZombieImpl(World world) {
        super(new EntityZombie(world));
    }

    @Override
    public boolean isBaby() {
        return entity.isBaby();
    }

    @Override
    public void setBaby(boolean baby) {
        entity.setBaby(baby);
    }
}