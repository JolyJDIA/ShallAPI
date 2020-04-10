package jolyjdia.nms.v1_15_R1.entity;

import jolyjdia.nms.interfaces.entity.DEntityVillager;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.EntityVillager;
import net.minecraft.server.v1_15_R1.VillagerProfession;
import net.minecraft.server.v1_15_R1.World;

public class DEntityVillagerImpl extends DEntityLivingBase<EntityVillager> implements DEntityVillager {

    public DEntityVillagerImpl(World world) {
        super(new EntityVillager(EntityTypes.VILLAGER, world));
    }

    @Override
    public VillagerProfession getProfession() {
        return entity.getVillagerData().getProfession();
    }

    @Override
    public void setVillagerProfession(VillagerProfession profession) {
        entity.setVillagerData(entity.getVillagerData().withProfession(profession));//todo: ЧЕКНУТЬ
    }
}
