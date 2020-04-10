package jolyjdia.nms.interfaces.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;

public interface PacketEntityLook extends DPacketEntity<DEntity> {

    void setPitch(byte pitch);

    void setYaw(byte yaw);

}
