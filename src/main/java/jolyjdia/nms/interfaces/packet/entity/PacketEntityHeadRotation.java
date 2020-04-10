package jolyjdia.nms.interfaces.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;

public interface PacketEntityHeadRotation extends DPacketEntity<DEntity> {

    void setYaw(byte yaw);
}
