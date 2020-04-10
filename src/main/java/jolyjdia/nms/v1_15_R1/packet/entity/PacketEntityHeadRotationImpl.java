package jolyjdia.nms.v1_15_R1.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.packet.entity.PacketEntityHeadRotation;
import jolyjdia.nms.v1_15_R1.entity.DEntityBase;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityHeadRotation;

public class PacketEntityHeadRotationImpl extends DPacketEntityBase<PacketPlayOutEntityHeadRotation, DEntity> implements PacketEntityHeadRotation {

    private byte yaw;

    public PacketEntityHeadRotationImpl(DEntity entity, byte yaw) {
        super(entity);
        this.yaw = yaw;
    }

    @Override
    public void setYaw(byte yaw) {
        this.yaw = yaw;
        init();
    }

    @Override
    protected PacketPlayOutEntityHeadRotation init() {
        return new PacketPlayOutEntityHeadRotation(((DEntityBase)entity).getEntityNms(), yaw);
    }
}
