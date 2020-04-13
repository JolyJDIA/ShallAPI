package jolyjdia.netty.packets.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import jolyjdia.netty.packet.PacketBuffer;
import jolyjdia.netty.packet.ClientPacket;
import jolyjdia.netty.packets.ProtocolMap;

import java.util.UUID;

/**
 * ДЛЯ ОТПРАВКИ НА СЕРВЕРЕ
 */
public class ClientGetGroupPacket implements ClientPacket {
    private UUID uuid;
    private int groupLvl;

    //получаю данные
    @Override
    public void readPacketData(PacketBuffer buf) {
        uuid = buf.readUniqueId();
        groupLvl = buf.readInt();
    }

    @Override
    public void writePacketData(PacketBuffer buf) {
        buf.writeVarInt(ProtocolMap.getServerPacketId(this));
        buf.writeUniqueId(uuid);
    }

    @Override
    public void sendPacket(Channel channel) {
       // PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
        //writePacketData(buf);
        channel.writeAndFlush(this);
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getGroupLvl() {
        return groupLvl;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
