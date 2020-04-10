package jolyjdia.api.craftentity.npc.types;

import jolyjdia.api.craftentity.npc.NPC;

public interface SlimeNPC extends NPC {

    /**
     * узнать размер слайма
     * @return - размер
     */
    int getSize();

    /**
     * установить размер слайма
     * только для SLIME
     */
    void setSize(int size);
}
