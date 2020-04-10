package jolyjdia.nms.interfaces.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.packet.DPacket;

public interface DPacketEntity<E extends DEntity> extends DPacket {

    E getEntity();

    void setEntity(E entity);
}
