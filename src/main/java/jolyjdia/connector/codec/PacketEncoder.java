package jolyjdia.connector.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import jolyjdia.connector.packet.ClientPacket;
import jolyjdia.connector.packet.PacketBuffer;
import org.jetbrains.annotations.NotNull;

public class PacketEncoder extends MessageToByteEncoder<ClientPacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, @NotNull ClientPacket packet, ByteBuf byteBuf) {
        System.out.println("encoder: "+packet);
        packet.writePacketData(new PacketBuffer(byteBuf));
    }
}
