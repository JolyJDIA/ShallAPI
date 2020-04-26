package jolyjdia.connector.packets.base;

import io.netty.channel.Channel;
import jolyjdia.connector.packet.AbstractPacketHandler;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.PlayerPacket;
import org.jetbrains.annotations.NotNull;

public class GamerGroupResponsePacket extends PlayerPacket {
    private static final int PACKET_ID = ProtocolMap.getServerPacketId(GamerGroupResponsePacket.class);
    private int groupLvl;

    @Override
    public void readPacketData(@NotNull PacketBuffer buf) {
        setUuid(buf.readUniqueId());
        groupLvl = buf.readInt();
    }

    @Override
    public void writePacketData(@NotNull PacketBuffer buf) {
        buf.writeVarInt(PACKET_ID);
        buf.writeUniqueId(getUuid());
        buf.writeInt(groupLvl);
    }

    @Override
    public void handle(@NotNull AbstractPacketHandler handler) {
        handler.handle(this);
    }

    public int getGroupLvl() {
        return groupLvl;
    }
}