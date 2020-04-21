package jolyjdia.connector.packets;

import io.netty.channel.Channel;
import jolyjdia.connector.packet.ClientPacket;
import jolyjdia.connector.packet.PacketBuffer;

public class ClientQueryPacket implements ClientPacket {
    private String sql;

    public ClientQueryPacket() {}

    /**
     *
     * @param sql
     */
    public ClientQueryPacket(final String sql) {
        this.sql = sql;
    }

    @Override
    public void readPacketData(PacketBuffer buf) {

    }

    @Override
    public void writePacketData(PacketBuffer buf) {

    }

    @Override
    public void sendPacket(Channel channel) {

    }
}
