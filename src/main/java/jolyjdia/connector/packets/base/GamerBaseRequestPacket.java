package jolyjdia.connector.packets.base;

import io.netty.channel.Channel;
import jolyjdia.connector.packet.AbstractPacketHandler;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.PlayerPacket;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GamerBaseRequestPacket extends PlayerPacket {
    private static final int PACKET_ID = ProtocolMap.getServerPacketId(GamerBaseRequestPacket.class);
    public GamerBaseRequestPacket(UUID uuid) {
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
