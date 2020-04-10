package jolyjdia.api.craftentity.npc.types;

import jolyjdia.api.craftentity.npc.NPC;

public interface ZombieNPC extends NPC {
    boolean isBaby();

    void setBaby(boolean baby);
}
