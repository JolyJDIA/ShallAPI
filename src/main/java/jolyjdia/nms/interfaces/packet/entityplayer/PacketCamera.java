package jolyjdia.nms.interfaces.packet.entityplayer;

import jolyjdia.nms.interfaces.packet.DPacket;
import org.bukkit.entity.Player;

public interface PacketCamera extends DPacket {

    Player getPlayer();

    void setPlayer(Player player);
}
