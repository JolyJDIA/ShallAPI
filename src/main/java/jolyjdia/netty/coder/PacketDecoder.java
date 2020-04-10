/*
package jolyjdia.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import jolyjdia.netty.packet.ClientPacket;
import jolyjdia.netty.packet.PacketBuffer;
import jolyjdia.netty.packet.ServerPacket;
import jolyjdia.netty.packets.Packets;

import java.util.List;

public class PacketDecoder extends MessageToMessageDecoder<DatagramPacket> {

    //Как получаю
    @Override
    protected void decode(ChannelHandlerContext context, DatagramPacket datagramPacket, List<Object> out) {
        ByteBuf byteBuf = datagramPacket.content();
        if (byteBuf.readableBytes() != 0) {
            PacketBuffer pbuf = new PacketBuffer(byteBuf);
            ClientPacket packet = Packets.getPacketClient(pbuf.readVarInt());

            System.out.println("decoder "+packet);
            if(packet == null) {
                return;
            }
            packet.readPacketData(pbuf);
            out.add(packet);
        }
    }
}
*/
