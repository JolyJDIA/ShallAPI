package jolyjdia.nms.interfaces.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;

public interface PacketAttachEntity extends DPacketEntity<DEntity> {

    void setVehicle(DEntity vehicle);

    DEntity getVehicle();
}
