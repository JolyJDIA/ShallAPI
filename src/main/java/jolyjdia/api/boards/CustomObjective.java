package jolyjdia.api.boards;

import jolyjdia.api.craft.PacketObject;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

public interface CustomObjective extends PacketObject {

    /**
     * Что показывать в качестве борда
     */
    void setDisplayName(String displayName);

    /**
     * где именно будет показываться надпись
     * @param displaySlot - где именно
     */
    void setDisplaySlot(DisplaySlot displaySlot);

    /**
     * поставить или удалить очки
     * @param player - кому
     * @param score - очки
     */
    void setScore(Player player, int score);
}
