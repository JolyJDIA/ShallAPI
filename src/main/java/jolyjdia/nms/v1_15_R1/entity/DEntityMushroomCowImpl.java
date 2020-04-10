package jolyjdia.nms.v1_15_R1.entity;

import jolyjdia.nms.interfaces.entity.DEntityMushroomCow;
import net.minecraft.server.v1_15_R1.EntityMushroomCow;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.World;

public class DEntityMushroomCowImpl extends DEntityLivingBase<EntityMushroomCow> implements DEntityMushroomCow {

    public DEntityMushroomCowImpl(World world) {
        super(new EntityMushroomCow(EntityTypes.MOOSHROOM, world));
    }
}

