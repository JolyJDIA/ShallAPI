package jolyjdia.nms.v1_15_R1.packet.scoreboard;

import jolyjdia.nms.interfaces.packet.scoreboard.PacketScoreboardObjective;
import jolyjdia.nms.scoreboard.DObjective;
import jolyjdia.nms.scoreboard.ObjectiveActionMode;
import jolyjdia.nms.util.ReflectionUtils;
import jolyjdia.nms.v1_15_R1.packet.DPacketBase;
import net.minecraft.server.v1_15_R1.ChatComponentText;
import net.minecraft.server.v1_15_R1.IScoreboardCriteria;
import net.minecraft.server.v1_15_R1.PacketPlayOutScoreboardObjective;

public class PacketScoreboardObjectiveImpl extends DPacketBase<PacketPlayOutScoreboardObjective> implements PacketScoreboardObjective {

    private DObjective objective;
    private ObjectiveActionMode mode;

    public PacketScoreboardObjectiveImpl(DObjective objective, ObjectiveActionMode mode) {
        this.objective = objective;
        this.mode = mode;
    }

    @Override
    public void setObjective(DObjective objective) {
        this.objective = objective;
        init();
    }

    @Override
    public void setMode(ObjectiveActionMode mode) {
        this.mode = mode;
        init();
    }

    @Override
    public DObjective getObjective() {
        return objective;
    }

    @Override
    public ObjectiveActionMode getMode() {
        return mode;
    }

    @Override
    protected PacketPlayOutScoreboardObjective init() {
        PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective();
        ReflectionUtils.setFieldValue(packet, "a", objective.getName());
        ReflectionUtils.setFieldValue(packet, "b", new ChatComponentText(objective.getDisplayName()));
        ReflectionUtils.setFieldValue(packet, "c", IScoreboardCriteria.EnumScoreboardHealthDisplay.valueOf(objective.getType().name()));
        ReflectionUtils.setFieldValue(packet, "d", mode.ordinal());
        return packet;
    }
}