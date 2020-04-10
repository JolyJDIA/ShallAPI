package jolyjdia.api.craftentity.npc.types;

import jolyjdia.api.craftentity.npc.NPC;

public interface VillagerNPC extends NPC {

    /**
     * узнать професси
     * @return - профессия
     */
    Profession getProfession();

    /**
     * только для VILLAGER
     * задать тип жителя
     */
    void setVillagerProfession(Profession profession);

    enum Profession {
        NORMAL,
        FARMER,
        LIBRARIAN,
        PRIEST,
        BLACKSMITH,
        BUTCHER,
        NITWIT,
        HUSK
    }
}
