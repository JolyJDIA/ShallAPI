package jolyjdia.nms.v1_15_R1.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.packet.entity.PacketAttachEntity;
import jolyjdia.nms.v1_15_R1.entity.DEntityBase;
import net.minecraft.server.v1_15_R1.PacketPlayOutAttachEntity;

public class PacketAttachEntityImpl extends DPacketEntityBase<PacketPlayOutAttachEntity, DEntity> implements PacketAttachEntity {

    private DEntity vehicle;

    public PacketAttachEntityImpl(DEntity entity, DEntity vehicle) {
        super(entity);
        this.vehicle = vehicle;
    }

    @Override
    public void setVehicle(DEntity vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public DEntity getEntity() {
        return super.getEntity();
    }

    @Override
    public DEntity getVehicle() {
        return vehicle;
    }

    @Override
    protected PacketPlayOutAttachEntity init() {
        return new PacketPlayOutAttachEntity(((DEntityBase)entity).getEntityNms(),
                ((DEntityBase)vehicle).getEntityNms());
    }
}
