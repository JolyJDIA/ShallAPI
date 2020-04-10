package jolyjdia.nms.interfaces.packet.scoreboard;

import jolyjdia.nms.interfaces.packet.DPacket;
import jolyjdia.nms.scoreboard.DObjective;
import jolyjdia.nms.scoreboard.ObjectiveActionMode;

public interface PacketScoreboardObjective extends DPacket {

    void setObjective(DObjective objective);

    DObjective getObjective();

    void setMode(ObjectiveActionMode mode);

    ObjectiveActionMode getMode();
}
