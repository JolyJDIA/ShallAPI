package jolyjdia.nms.v1_15_R1.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.packet.entity.DPacketEntity;
import jolyjdia.nms.v1_15_R1.packet.DPacketBase;
import net.minecraft.server.v1_15_R1.Packet;

public abstract class DPacketEntityBase<A extends Packet, T extends DEntity> extends DPacketBase<A> implements DPacketEntity<T> {

    protected T entity;

    public DPacketEntityBase(T entity) {
        this.entity = entity;
    }

    @Override
    public void setEntity(T entity) {
        this.entity = entity;
        init();
    }

    @Override
    public T getEntity() {
        return entity;
    }
}