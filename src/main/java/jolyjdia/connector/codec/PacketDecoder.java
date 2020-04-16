package jolyjdia.connector.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import jolyjdia.connector.packet.ClientPacket;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packets.ProtocolMap;

import java.util.List;

public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        System.out.println("decoder");
        PacketBuffer packetBuffer = new PacketBuffer(msg);
        ClientPacket packet = ProtocolMap.getPacketServer(packetBuffer.readVarInt());
        if(packet == null) {
            return;
        }
        packet.readPacketData(packetBuffer);
        out.add(packet);
    }
}
