package jolyjdia.nms.v1_15_R1.entity;


import jolyjdia.nms.interfaces.entity.DEntityBlaze;
import net.minecraft.server.v1_15_R1.EntityBlaze;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.World;

public class DEntityBlazeImpl extends DEntityLivingBase<EntityBlaze> implements DEntityBlaze {

    public DEntityBlazeImpl(World world) {
        super(new EntityBlaze(EntityTypes.BLAZE, world));
    }
}
