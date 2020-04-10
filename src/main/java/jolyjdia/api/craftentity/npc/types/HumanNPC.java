package jolyjdia.api.craftentity.npc.types;

import jolyjdia.api.craftentity.npc.NPC;
import jolyjdia.api.skin.Skin;
import org.bukkit.ChatColor;

public interface HumanNPC extends NPC {

    /**
     * сменить скин
     * @param value & signatire - данные скина
     * @param skin - скин который ставить
     */
    void changeSkin(String value, String signature);
    void changeSkin(Skin skin);

    void setBed(boolean bed);
    boolean isLeavedBed();

    void setGlowing(ChatColor chatColor); //сразу включить и задать цвет(по умолчанию AQUA)

    /**
     * получить скин который стоит у данного НПС
     * @return - скин
     */
    Skin getSkin();
}
