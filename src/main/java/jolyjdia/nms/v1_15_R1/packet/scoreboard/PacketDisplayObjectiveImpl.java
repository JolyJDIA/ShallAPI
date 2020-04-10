package jolyjdia.nms.v1_15_R1.packet.scoreboard;

import jolyjdia.nms.interfaces.packet.scoreboard.PacketDisplayObjective;
import jolyjdia.nms.scoreboard.DObjective;
import jolyjdia.nms.util.ReflectionUtils;
import jolyjdia.nms.v1_15_R1.packet.DPacketBase;
import net.minecraft.server.v1_15_R1.PacketPlayOutScoreboardDisplayObjective;
import org.bukkit.scoreboard.DisplaySlot;

public class PacketDisplayObjectiveImpl extends DPacketBase<PacketPlayOutScoreboardDisplayObjective> implements PacketDisplayObjective {

    private DisplaySlot slot;
    private DObjective objective;

    public PacketDisplayObjectiveImpl(DisplaySlot slot, DObjective objective) {
        this.slot = slot;
        this.objective = objective;
    }

    @Override
    public void setObjective(DObjective objective) {
        this.objective = objective;
        init();
    }

    @Override
    public void setDisplaySlot(DisplaySlot slot) {
        this.slot = slot;
        init();
    }

    @Override
    public DisplaySlot getSlot() {
        return slot;
    }

    @Override
    public DObjective getObjective() {
        return objective;
    }

    @Override
    protected PacketPlayOutScoreboardDisplayObjective init() {
        PacketPlayOutScoreboardDisplayObjective packet = new PacketPlayOutScoreboardDisplayObjective();
        ReflectionUtils.setFieldValue(packet, "a", 1);//todo slot.original
        ReflectionUtils.setFieldValue(packet, "b", objective.getName());

        return packet;
    }
}
