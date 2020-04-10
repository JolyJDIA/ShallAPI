package jolyjdia.nms.v1_15_R1.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.packet.entity.PacketSpawnEntity;
import jolyjdia.nms.types.EntitySpawnType;
import jolyjdia.nms.v1_15_R1.entity.DEntityBase;
import net.minecraft.server.v1_15_R1.PacketPlayOutSpawnEntity;

public class PacketSpawnEntityImpl extends DPacketEntityBase<PacketPlayOutSpawnEntity, DEntity>
        implements PacketSpawnEntity {

    private EntitySpawnType entitySpawnType;
    private int objectData;

    public PacketSpawnEntityImpl(DEntity entity, EntitySpawnType entitySpawnType, int objectData) {
        super(entity);
        this.entitySpawnType = entitySpawnType;
        this.objectData = objectData;
    }

    @Override
    public void setEntitySpawnType(EntitySpawnType entitySpawnType) {
        this.entitySpawnType = entitySpawnType;
        init();
    }

    @Override
    public void setObjectData(int objectData) {
        this.objectData = objectData;
        init();
    }

    @Override
    public DEntity getEntity() {
        return super.getEntity();
    }

    @Override
    public EntitySpawnType getEntitySpawnType() {
        return entitySpawnType;
    }

    @Override
    public int getObjectData() {
        return objectData;
    }

    @Override
    protected PacketPlayOutSpawnEntity init() {
        return new PacketPlayOutSpawnEntity(((DEntityBase)entity).getEntityNms(), entitySpawnType.getId());//objectData
    }
}
