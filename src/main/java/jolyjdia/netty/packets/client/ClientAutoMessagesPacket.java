package jolyjdia.netty.packets.client;

import io.netty.channel.Channel;
import jolyjdia.netty.packet.PacketBuffer;
import jolyjdia.netty.packet.ServerPacket;
import org.jetbrains.annotations.NotNull;

public class ClientAutoMessagesPacket implements ServerPacket {
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