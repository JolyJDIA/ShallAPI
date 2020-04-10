package jolyjdia.nms.v1_15_R1.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.packet.entity.PacketEntityMetadata;
import jolyjdia.nms.v1_15_R1.entity.DEntityBase;
import net.minecraft.server.v1_15_R1.Entity;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityMetadata;

public class PacketEntityMetadataImpl extends DPacketEntityBase<PacketPlayOutEntityMetadata, DEntity> implements PacketEntityMetadata {

    public PacketEntityMetadataImpl(DEntity entity) {
        super(entity);
    }

    @Override
    protected PacketPlayOutEntityMetadata init() {
        Entity entityNms = ((DEntityBase)this.entity).getEntityNms();
        return new PacketPlayOutEntityMetadata(entityNms.getId(), entityNms.getDataWatcher(), true);
    }
}
