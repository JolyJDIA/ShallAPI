package jolyjdia.nms.interfaces.entity;

import net.minecraft.server.v1_15_R1.VillagerProfession;

public interface DEntityVillager extends DEntityLiving {

    VillagerProfession getProfession();

    void setVillagerProfession(VillagerProfession profession);
}
