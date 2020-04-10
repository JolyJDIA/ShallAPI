package jolyjdia.netty.packets.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import jolyjdia.netty.packet.PacketBuffer;
import jolyjdia.netty.packet.ServerPacket;
import jolyjdia.netty.packets.Packets;

import java.util.UUID;

/**
 * Этот пакет преднозначен для сервера(то есть в клиенте юзается)
 * ЭТОТ ПАКЕТ ОБНОВЛЯЕТ ГРУППУ ИГРОКА НА СЕРВЕРЕ
 */
public class ClientUpdateGroupPacket implements ServerPacket {

    private UUID uuid;
    private int groupLvl;


    @Override
    public void readPacketData(PacketBuffer buf) {
        uuid = buf.readUniqueId();
        groupLvl = buf.readInt();
    }

    @Override
    public void writePacketData(PacketBuffer buf) {
        buf.writeVarInt(Packets.getServerPacketId(this));
        buf.writeUniqueId(uuid);
        buf.writeInt(groupLvl);
    }

    @Override
    public void sendPacket(Channel channel) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        writePacketData(packetBuffer);

        channel.writeAndFlush(packetBuffer);
    }

    public int getGroupLvl() {
        return groupLvl;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setGroupLvl(int groupLvl) {
        this.groupLvl = groupLvl;
    }
}
