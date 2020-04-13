package jolyjdia.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import jolyjdia.netty.packet.ClientPacket;
import jolyjdia.netty.packet.PacketBuffer;
import org.jetbrains.annotations.NotNull;

public class PacketEncoder extends MessageToByteEncoder<ClientPacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, @NotNull ClientPacket packet, ByteBuf byteBuf) {
        packet.writePacketData(new PacketBuffer(byteBuf));
    }
}
