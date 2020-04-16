package jolyjdia.connector.packets.client;

import io.netty.channel.Channel;
import jolyjdia.connector.packet.ClientPacket;
import jolyjdia.connector.packet.PacketBuffer;
import org.jetbrains.annotations.NotNull;

public class ClientAutoMessagesPacket implements ClientPacket {
    private String text;

    @Override
    public void readPacketData(@NotNull PacketBuffer buf) {
        text = buf.readString(354);
    }

    @Override
    public void writePacketData(PacketBuffer buf) {}

    @Override
    public void sendPacket(Channel channel) {}

    public String getText() {
        return text;
    }
}