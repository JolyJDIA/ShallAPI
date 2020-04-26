package jolyjdia.connector.packets.base;

import io.netty.channel.Channel;
import jolyjdia.connector.AbstractPacketHandler;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.PlayerPacket;
import org.jetbrains.annotations.NotNull;

public class GamerChangeGroupPacket extends PlayerPacket {
    private int groupLvl;

    @Override
    public void readPacketData(@NotNull PacketBuffer buf) {
        setUuid(buf.readUniqueId());
        groupLvl = buf.readInt();
    }

    @Override
    public void writePacketData(@NotNull PacketBuffer buf) {
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

    public void setGroupLvl(int groupLvl) {
        this.groupLvl = groupLvl;
    }
}
