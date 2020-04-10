package jolyjdia.nms.interfaces.packet.scoreboard;

import jolyjdia.nms.interfaces.packet.DPacket;
import jolyjdia.nms.scoreboard.DTeam;
import jolyjdia.nms.scoreboard.TeamAction;

public interface PacketScoreBoardTeam extends DPacket {

    void setTeam(DTeam team);
    DTeam getTeam();

    void setTeamAction(TeamAction action);
    TeamAction getTeamAction();
}
