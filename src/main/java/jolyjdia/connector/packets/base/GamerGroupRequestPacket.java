package jolyjdia.connector.packets.base;

import io.netty.channel.Channel;
import jolyjdia.connector.AbstractPacketHandler;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.PlayerPacket;

import java.util.UUID;

public class GamerGroupRequestPacket extends PlayerPacket {

    public GamerGroupRequestPacket(UUID uuid) {
        super(uuid);
    }

    @Override
    public void readPacketData(PacketBuffer buf) {
        setUuid(buf.readUniqueId());
    }

    @Override
    public void writePacketData(PacketBuffer buf) {
        buf.writeVarInt(ProtocolMap.getServerPacketId(this));
        buf.writeUniqueId(getUuid());
    }

    @Override
    public void handle(AbstractPacketHandler handler, Channel channel) {
        handler.handle(this, channel);
    }
}
