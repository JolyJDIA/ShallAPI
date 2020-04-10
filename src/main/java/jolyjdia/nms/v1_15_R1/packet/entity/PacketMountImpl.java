package jolyjdia.nms.v1_15_R1.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.packet.entity.PacketMount;
import jolyjdia.nms.v1_15_R1.entity.DEntityBase;
import net.minecraft.server.v1_15_R1.PacketPlayOutMount;

public class PacketMountImpl extends DPacketEntityBase<PacketPlayOutMount, DEntity> implements PacketMount {

    public PacketMountImpl(DEntity entity) {
        super(entity);
    }

    @Override
    protected PacketPlayOutMount init() {
        return new PacketPlayOutMount(((DEntityBase)entity).getEntityNms());
    }
}
