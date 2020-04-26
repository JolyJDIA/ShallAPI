package jolyjdia.connector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import jolyjdia.Main;
import jolyjdia.connector.packet.AbstractPacketHandler;
import jolyjdia.connector.packet.ClientPacket;
import org.jetbrains.annotations.NotNull;

public class NetworkManager extends SimpleChannelInboundHandler<ClientPacket> {
    private final AbstractPacketHandler handler;

    public NetworkManager(AbstractPacketHandler handler) {
        this.handler = handler;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext context, @NotNull ClientPacket packet) {
        packet.handle(handler);
    }
    public static void sendPacket(ClientPacket packet) {
        Main.getInstance().getChannel().writeAndFlush(packet);
    }
}
