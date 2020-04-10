package jolyjdia.nms.v1_15_R1.entity;

import jolyjdia.nms.interfaces.entity.DEntitySlime;
import net.minecraft.server.v1_15_R1.EntitySlime;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.World;

public class DEntitySlimeImpl extends DEntityLivingBase<EntitySlime> implements DEntitySlime {

    public DEntitySlimeImpl(World world) {
        super(new EntitySlime(EntityTypes.SLIME, world));
    }

    @Override
    public int getSize() {
        return entity.getSize();
    }

    @Override
    public void setSize(int size) {
        entity.setSize(size, true);
    }
}
