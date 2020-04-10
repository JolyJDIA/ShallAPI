/*
package jolyjdia.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import jolyjdia.netty.packet.ClientPacket;
import jolyjdia.netty.packet.PacketBuffer;
import jolyjdia.netty.packet.ServerPacket;
import jolyjdia.netty.packets.Packets;

import java.net.InetSocketAddress;
import java.util.List;

public class PacketEncoder extends MessageToMessageEncoder<AddressedEnvelope<ByteBuf, InetSocketAddress>> {
    //Как отправляю
    @Override
    protected void encode(ChannelHandlerContext context, AddressedEnvelope<ByteBuf, InetSocketAddress> envelope, List<Object> list) {
        PacketBuffer buffer = new PacketBuffer(envelope.content());
        ServerPacket packet = Packets.getPacketServer(buffer.readVarInt(), envelope.sender());
        if (packet == null) {
            return;
        }
        System.out.println("encoder " + packet);
        // DatagramPacket datagramPacket = new DatagramPacket(buffer, envelope.sender());
        // packet.writePacketData(buffer);
        list.add(packet);
    }
}
*/
