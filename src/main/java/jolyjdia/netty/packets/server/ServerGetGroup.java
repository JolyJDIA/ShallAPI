package jolyjdia.netty.packets.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import jolyjdia.netty.packet.ClientPacket;
import jolyjdia.netty.packet.PacketBuffer;
import jolyjdia.netty.packets.Packets;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * ОТ СЕРВЕРЕ КЛИЕНТУ
 * СЕРВЕР ДАЕТ ГРУППУ ИГРОКА
 */
public class ServerGetGroup implements ClientPacket {
    private UUID uuid;

    @Override
    public void readPacketData(PacketBuffer buf) {
        uuid = buf.readUniqueId();
    }

    @Override
    public void sendPacket(Channel channel, InetSocketAddress address) {}

    public UUID getUuid() {
        return uuid;
    }
}
