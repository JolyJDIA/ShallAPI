package jolyjdia.nms.interfaces.packet.scoreboard;

import jolyjdia.nms.interfaces.packet.DPacket;
import jolyjdia.nms.scoreboard.DObjective;
import org.bukkit.scoreboard.DisplaySlot;

public interface PacketDisplayObjective extends DPacket {

    void setObjective(DObjective objective);

    DObjective getObjective();

    void setDisplaySlot(DisplaySlot slot);

    DisplaySlot getSlot();
}
