package jolyjdia.nms.interfaces.packet;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface DPacket {

    void sendPacket(Player player);

    default void sendPacket(Player... players) {
        for(Player player : players) {
            sendPacket(player);
        }
    }
}
