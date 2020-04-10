package jolyjdia.nms.interfaces.packet.scoreboard;

import jolyjdia.nms.interfaces.packet.DPacket;
import jolyjdia.nms.scoreboard.DScore;
import jolyjdia.nms.scoreboard.ScoreboardAction;

public interface PacketScoreboardScore extends DPacket {

    DScore getScore();

    void setScore(DScore score);

    ScoreboardAction getAction();

    void setAction(ScoreboardAction action);
}
