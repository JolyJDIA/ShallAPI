package jolyjdia.nms.v1_15_R1.packet.scoreboard;

import jolyjdia.nms.interfaces.packet.scoreboard.PacketScoreboardScore;
import jolyjdia.nms.scoreboard.DScore;
import jolyjdia.nms.scoreboard.ScoreboardAction;
import jolyjdia.nms.util.ReflectionUtils;
import jolyjdia.nms.v1_15_R1.packet.DPacketBase;
import net.minecraft.server.v1_15_R1.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_15_R1.ScoreboardServer;

public class PacketScoreboardScoreImpl extends DPacketBase<PacketPlayOutScoreboardScore> implements PacketScoreboardScore {

    private DScore score;
    private ScoreboardAction action;

    public PacketScoreboardScoreImpl(DScore score, ScoreboardAction action) {
        this.score = score;
        this.action = action;
    }

    @Override
    public void setScore(DScore score) {
        this.score = score;
        init();
    }

    @Override
    public void setAction(ScoreboardAction action) {
        this.action = action;
        init();
    }

    @Override
    public DScore getScore() {
        return score;
    }

    @Override
    public ScoreboardAction getAction() {
        return action;
    }

    @Override
    protected PacketPlayOutScoreboardScore init() {
        PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore();
        ReflectionUtils.setFieldValue(packet, "a", score.getName());
        ReflectionUtils.setFieldValue(packet, "b", score.getDObjective().getName());
        ReflectionUtils.setFieldValue(packet, "c", score.getScore());
        ReflectionUtils.setFieldValue(packet, "d", ScoreboardServer.Action.valueOf(action.name()));

        return packet;
    }
}
