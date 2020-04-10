package jolyjdia.nms.interfaces.entity;

import jolyjdia.api.craftentity.npc.types.EnderDragonNPC;

public interface DEntityDragon extends DEntityLiving {

    EnderDragonNPC.Phase getPhase();

    void setPhase(EnderDragonNPC.Phase phase);
}
