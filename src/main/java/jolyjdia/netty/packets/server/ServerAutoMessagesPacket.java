package jolyjdia.netty.packets.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import jolyjdia.netty.packet.ClientPacket;
import jolyjdia.netty.packet.PacketBuffer;
import jolyjdia.netty.packets.Packets;

import java.net.InetSocketAddress;

public class ServerAutoMessagesPacket implements ClientPacket {
    private final String text;
    public ServerAutoMessagesPacket(String text) {
        this.text = text;
    }
    @Override
    public void readPacketData(PacketBuffer buf) {}

    @Override
    public void sendPacket(Channel channel, InetSocketAddress address) {
        PacketBuffer buf = new PacketBuffer(Unpooled.buffer());

        buf.writeVarInt(Packets.getClientPacketId(this));
        buf.writeString(text);

        DatagramPacket datagramPacket = new DatagramPacket(buf, address);

        channel.writeAndFlush(datagramPacket);
    }
}