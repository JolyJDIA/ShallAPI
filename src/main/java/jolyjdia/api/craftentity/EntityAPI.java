package jolyjdia.api.craftentity;

import jolyjdia.api.craftentity.npc.NPC;
import jolyjdia.api.craftentity.npc.NpcType;
import jolyjdia.api.craftentity.npc.types.HumanNPC;
import jolyjdia.api.craftentity.stand.CustomStand;
import jolyjdia.api.skin.Skin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface EntityAPI {

    /**
     * Создать армор стенд
     * по умолчанию скрыт для всех
     * @param location - локация для создания армор стенда
     * @return - вернет CustomStand
     */
    CustomStand createStand(Location location);

    /**
     * Создать НПС HUMAN
     * по умолчанию скрыт для всех
     * @param location - локация для энтити
     * @param value - часть скина(голова)
     * @param signature - часть скина(тело)
     * @return - вернет NPC
     */
    HumanNPC createNPC(Location location, String value, String signature);
    HumanNPC createNPC(Location location, Player player);
    HumanNPC createNPC(Location location, Skin skin);

    /**
     * Создать НПС
     * по умолчанию скрыт для всех
     * @param location - локация для энтити
     * @param type - тип НПС
     * @return - вернет NPC
     */
    <T extends NPC> T createNPC(Location location, NpcType type);

    List<NPC> getNPCs();

    <T extends NPC> List<T> getNPC(NpcType type);

    List<CustomStand> getCustomStands();
}
