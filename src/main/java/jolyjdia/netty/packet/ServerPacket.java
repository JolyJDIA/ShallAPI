package jolyjdia.netty.packet;

import io.netty.channel.Channel;

public interface ServerPacket {

    void readPacketData(PacketBuffer buf);

    void writePacketData(PacketBuffer buf);
    void sendPacket(Channel channel);
}