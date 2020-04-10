package jolyjdia.nms.v1_15_R1.packet.entityplayer;

import jolyjdia.nms.interfaces.packet.entityplayer.PacketCamera;
import jolyjdia.nms.v1_15_R1.packet.DPacketBase;
import net.minecraft.server.v1_15_R1.PacketPlayOutCamera;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.entity.Player;

public class PacketCameraImpl extends DPacketBase<PacketPlayOutCamera> implements PacketCamera {

    private Player player;

    public PacketCameraImpl(Player player) {
        this.player = player;
    }

    @Override
    protected PacketPlayOutCamera init() {
        return new PacketPlayOutCamera(((CraftEntity) player).getHandle());
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
        init();
    }
}
