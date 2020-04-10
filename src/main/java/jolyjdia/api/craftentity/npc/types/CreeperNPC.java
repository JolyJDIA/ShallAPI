package jolyjdia.api.craftentity.npc.types;

import jolyjdia.api.craftentity.npc.NPC;

public interface CreeperNPC extends NPC {

    /**
     * надутый или нет
     *
     * @return - крипер
     */
    boolean isPowered();

    /**
     * сделать надутым
     *
     * @param flag - какой крипер сейчас
     */
    void setPowered(boolean flag);
}