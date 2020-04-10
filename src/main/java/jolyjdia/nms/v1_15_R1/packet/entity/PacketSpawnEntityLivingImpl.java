package jolyjdia.nms.v1_15_R1.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntityLiving;
import jolyjdia.nms.interfaces.packet.entity.PacketSpawnEntityLiving;
import jolyjdia.nms.util.ReflectionUtils;
import jolyjdia.nms.v1_15_R1.entity.DEntityLivingBase;
import net.minecraft.server.v1_15_R1.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PacketSpawnEntityLivingImpl extends DPacketEntityBase<PacketPlayOutSpawnEntityLiving, DEntityLiving> implements PacketSpawnEntityLiving {

    private static final double Y = 0.8;

   // private Version versionSender = Version.EMPTY;

    public PacketSpawnEntityLivingImpl(DEntityLiving entity) {
        super(entity);
    }

    @Override
    protected PacketPlayOutSpawnEntityLiving init() {
        Location location = entity.getLocation();

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(
                ((DEntityLivingBase)entity).getEntityNms());
        if(entity.hasPassenger()) {
            ReflectionUtils.setFieldValue(packet, "e", location.getY() + Y);
        }
        /*if (this.versionSender.getVersion() >= Version.MINECRAFT_1_11.getVersion() - 1 && entity.hasPassenger())
            ReflectionUtils.setFieldValue(packet, "e", location.getY() + Y);
        if (this.versionSender.getVersion() >= Version.MINECRAFT_1_9.getVersion()
                && this.versionSender.getVersion() <= Version.MINECRAFT_1_10.getVersion()
                && !entity.hasPassenger()) {
            if (!(entity instanceof DEntityArmorStand))
                ReflectionUtils.setFieldValue(packet, "e", location.getY() - Y - 0.1);

            DEntityArmorStand entityArmorStand = (DEntityArmorStand) entity;
            if (entityArmorStand.isInvisible())
                ReflectionUtils.setFieldValue(packet, "e", location.getY() - Y - 0.1);
        }*/


        return packet;
    }

    @Override
    public void sendPacket(Player player) {
        //fixLocation(player);
        super.sendPacket(player);
    }

    /*private void fixLocation(Player player) {
        //проверять с какой версии
        GamePlayer gamer = GAMER_MANAGER.getGamer(player);
        this.versionSender = Version.EMPTY;
        if (gamer == null)
            return;

        this.versionSender = gamer.getVersion();
    }*/
}
