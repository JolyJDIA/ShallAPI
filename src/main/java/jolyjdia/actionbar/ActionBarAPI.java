package jolyjdia.actionbar;

import org.bukkit.entity.Player;

import java.util.Collection;

public interface ActionBarAPI {
    /**
     * отправить бар игроку
     * @param player - кому слать
     * @param message - какое сообщение
     */
    void sendBar(Player player, String message);

    /**
     * Отправить сообщение игрокам
     * @param players - кому слать
     * @param message - какое сообщение
     */
    void sendBar(Collection<? extends Player> players, String message);

    /**
     * Отправить анимированное оообщение
     * @param player - кому слать
     * @param message - какое сообщение
     */
    @Deprecated
    void sendAnimatedBar(Player player, String message);
}
