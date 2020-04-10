package jolyjdia.netty.packet;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

public interface ClientPacket {
    void readPacketData(PacketBuffer buf);
    void sendPacket(Channel channel, InetSocketAddress address);
}
