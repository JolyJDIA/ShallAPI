package jolyjdia.connector.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import jolyjdia.connector.packet.ClientPacket;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, @NotNull ByteBuf msg, List<Object> out) {
        if (msg.readableBytes() != 0) {
            PacketBuffer pbuf = new PacketBuffer(msg);
            int id = pbuf.readVarInt();
            ClientPacket packet = ProtocolMap.getPacketServer(id);
            if(packet == null) {
                return;
            }
            packet.readPacketData(pbuf);
            out.add(packet);
        }
    }
}
