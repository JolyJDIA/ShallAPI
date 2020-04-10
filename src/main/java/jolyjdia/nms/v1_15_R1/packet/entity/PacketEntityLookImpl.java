package jolyjdia.nms.v1_15_R1.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.packet.entity.PacketEntityLook;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntity;

public class PacketEntityLookImpl extends DPacketEntityBase<PacketPlayOutEntity.PacketPlayOutEntityLook, DEntity> implements PacketEntityLook {

    private byte pitch;
    private byte yaw;

    public PacketEntityLookImpl(DEntity entity, byte yaw, byte pitch) {
        super(entity);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void setPitch(byte pitch) {
        this.pitch = pitch;
        init();
    }

    @Override
    public void setYaw(byte yaw) {
        this.yaw = yaw;
        init();
    }

    @Override
    protected PacketPlayOutEntity.PacketPlayOutEntityLook init() {
        return new PacketPlayOutEntity.PacketPlayOutEntityLook(entity.getEntityID(), yaw, pitch, true);
    }
}
