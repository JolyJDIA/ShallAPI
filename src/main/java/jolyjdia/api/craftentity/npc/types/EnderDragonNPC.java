package jolyjdia.api.craftentity.npc.types;

import jolyjdia.api.craftentity.npc.NPC;

public interface EnderDragonNPC extends NPC {
    Phase getPhase();

    void setPhase(Phase phase);

    enum Phase {
        CIRCLING,
        STRAFING,
        FLY_TO_PORTAL,
        LAND_ON_PORTAL,
        LEAVE_PORTAL,
        BREATH_ATTACK,
        SEARCH_FOR_BREATH_ATTACK_TARGET,
        ROAR_BEFORE_ATTACK,
        CHARGE_PLAYER,
        DYING,
        HOVER

    }
}
