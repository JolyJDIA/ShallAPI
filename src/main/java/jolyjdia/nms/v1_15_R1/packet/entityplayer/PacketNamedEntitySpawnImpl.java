package jolyjdia.nms.v1_15_R1.packet.entityplayer;

import jolyjdia.nms.interfaces.entity.DEntityPlayer;
import jolyjdia.nms.interfaces.packet.entityplayer.PacketNamedEntitySpawn;
import jolyjdia.nms.v1_15_R1.entity.DEntityLivingBase;
import jolyjdia.nms.v1_15_R1.packet.entity.DPacketEntityBase;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.PacketPlayOutNamedEntitySpawn;

public class PacketNamedEntitySpawnImpl extends DPacketEntityBase<PacketPlayOutNamedEntitySpawn, DEntityPlayer>
        implements PacketNamedEntitySpawn {

    public PacketNamedEntitySpawnImpl(DEntityPlayer entity) {
        super(entity);
    }

    @Override
    protected PacketPlayOutNamedEntitySpawn init() {
        return new PacketPlayOutNamedEntitySpawn(((DEntityLivingBase<EntityPlayer>) entity).getEntityNms());
    }
}
