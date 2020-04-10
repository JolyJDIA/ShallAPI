package jolyjdia.nms.v1_15_R1.entity;

import jolyjdia.nms.interfaces.entity.DEntityCreeper;
import net.minecraft.server.v1_15_R1.EntityCreeper;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.World;

public class DEntityCreeperImpl extends DEntityLivingBase<EntityCreeper> implements DEntityCreeper {

    public DEntityCreeperImpl(World world) {
        super(new EntityCreeper(EntityTypes.CREEPER, world));
    }

    @Override
    public boolean isPowered() {
        return entity.isPowered();
    }

    @Override
    public void setPowered(boolean powered) {
        entity.setPowered(powered);
    }
}
