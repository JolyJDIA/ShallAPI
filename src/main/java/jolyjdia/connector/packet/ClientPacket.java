package jolyjdia.connector.packet;

import io.netty.channel.Channel;
import jolyjdia.connector.AbstractPacketHandler;

public interface ClientPacket {
    void readPacketData(PacketBuffer buf);
    void writePacketData(PacketBuffer buf);
    void handle(AbstractPacketHandler handler, Channel channel);
}
