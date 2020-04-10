package jolyjdia.nms.interfaces.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.types.EntitySpawnType;

public interface PacketSpawnEntity extends DPacketEntity<DEntity> {

    EntitySpawnType getEntitySpawnType();

    void setEntitySpawnType(EntitySpawnType entitySpawnType);

    int getObjectData();

    void setObjectData(int objectData);
}
