package jolyjdia.nms.v1_15_R1.packet.entity;

import jolyjdia.nms.interfaces.packet.entity.PacketEntityDestroy;
import jolyjdia.nms.v1_15_R1.packet.DPacketBase;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityDestroy;

public class PacketEntityDestroyImpl extends DPacketBase<PacketPlayOutEntityDestroy> implements PacketEntityDestroy {

    private final int[] entityIDs;

    public PacketEntityDestroyImpl(int... entityIDs) {
        this.entityIDs = entityIDs;
    }

    @Override
    protected PacketPlayOutEntityDestroy init() {
        return new PacketPlayOutEntityDestroy(entityIDs);
    }
}
