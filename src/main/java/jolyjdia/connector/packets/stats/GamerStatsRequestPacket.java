package jolyjdia.connector.packets.stats;

import io.netty.channel.Channel;
import jolyjdia.connector.packet.AbstractPacketHandler;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.PlayerPacket;
import jolyjdia.connector.packets.base.GamerGroupResponsePacket;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GamerStatsRequestPacket extends PlayerPacket {
    private static final int PACKET_ID = ProtocolMap.getServerPacketId(GamerStatsRequestPacket.class);
    public GamerStatsRequestPacket(UUID uuid) {
        super(uuid);
    }

    @Override
    public void readPacketData(@NotNull PacketBuffer buf) {
        setUuid(buf.readUniqueId());
    }

    @Override
    public void writePacketData(@NotNull PacketBuffer buf) {
        buf.writeVarInt(PACKET_ID);
        buf.writeUniqueId(getUuid());
    }

    @Override
    public void handle(@NotNull AbstractPacketHandler handler) {
        handler.handle(this);
    }
}
