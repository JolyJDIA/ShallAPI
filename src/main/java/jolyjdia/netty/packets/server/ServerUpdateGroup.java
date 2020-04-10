package jolyjdia.netty.packets.server;

import io.netty.channel.Channel;
import jolyjdia.netty.packet.ClientPacket;
import jolyjdia.netty.packet.PacketBuffer;
import jolyjdia.netty.packet.ServerPacket;
import jolyjdia.netty.packets.Packets;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * ОТ СЕРВЕРА КЛИЕНТУ
 */
public class ServerUpdateGroup implements ClientPacket {

    private UUID uuid;
    private int groupLvl;

    @Override
    public void readPacketData(PacketBuffer buf) {
        uuid = buf.readUniqueId();
        groupLvl = buf.readInt();
    }

    @Override
    public void sendPacket(Channel channel, InetSocketAddress address) {}

    public int getGroupLvl() {
        return groupLvl;
    }

    public UUID getUuid() {
        return uuid;
    }
}
