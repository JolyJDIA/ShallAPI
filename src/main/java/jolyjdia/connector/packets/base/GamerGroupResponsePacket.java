package jolyjdia.connector.packets.base;

import io.netty.channel.Channel;
import jolyjdia.connector.AbstractPacketHandler;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.PlayerPacket;

public class GamerGroupResponsePacket extends PlayerPacket {
    private int groupLvl;

    @Override
    public void readPacketData(PacketBuffer buf) {
        setUuid(buf.readUniqueId());
        groupLvl = buf.readInt();
    }

    @Override
    public void writePacketData(PacketBuffer buf) {
        buf.writeVarInt(ProtocolMap.getServerPacketId(this));
        buf.writeUniqueId(getUuid());
        buf.writeInt(groupLvl);
    }

    @Override
    public void handle(AbstractPacketHandler handler, Channel channel) {
        handler.handle(this, channel);
    }

    public int getGroupLvl() {
        return groupLvl;
    }
}