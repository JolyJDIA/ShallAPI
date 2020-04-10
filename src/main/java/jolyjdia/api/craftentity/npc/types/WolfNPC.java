package jolyjdia.api.craftentity.npc.types;

import jolyjdia.api.craftentity.npc.NPC;
import org.bukkit.DyeColor;

public interface WolfNPC extends NPC {

    DyeColor getCollarColor();

    void setCollarColor(DyeColor color);

    /**
     * злой или нет
     * @return - да или нет
     */
    boolean isAngry();

    /**
     * сделать злым
     * @param angry - да или нет
     */
    void setAngry(boolean angry);

    boolean isSitting();

    void setSitting(boolean sitting);
}
