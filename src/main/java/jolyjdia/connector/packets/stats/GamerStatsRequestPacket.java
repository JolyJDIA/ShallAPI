package jolyjdia.connector.packets.stats;

import io.netty.channel.Channel;
import jolyjdia.connector.AbstractPacketHandler;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.PlayerPacket;
import jolyjdia.connector.packets.base.GamerBaseRequestPacket;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GamerStatsRequestPacket extends PlayerPacket {

    public GamerStatsRequestPacket(UUID uuid) {
        super(uuid);
    }

    @Override
    public void readPacketData(@NotNull PacketBuffer buf) {
        setUuid(buf.readUniqueId());
    }

    @Override
    public void writePacketData(@NotNull PacketBuffer buf) {
        buf.writeVarInt(ProtocolMap.getServerPacketId(this));
    }

    @Override
    public void handle(@NotNull AbstractPacketHandler handler, Channel channel) {
        handler.handle(this, channel);
    }
}
