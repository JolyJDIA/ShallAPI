package jolyjdia.nms.v1_15_R1.entity;

import jolyjdia.nms.interfaces.entity.DEntityCow;
import net.minecraft.server.v1_15_R1.EntityCow;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.World;

public class DEntityCowImpl extends DEntityLivingBase<EntityCow> implements DEntityCow {

    public DEntityCowImpl(World world) {
        super(new EntityCow(EntityTypes.COW, world));
    }
}
