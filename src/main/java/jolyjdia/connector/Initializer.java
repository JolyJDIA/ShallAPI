package jolyjdia.connector;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import jolyjdia.connector.codec.PacketDecoder;
import jolyjdia.connector.codec.PacketEncoder;
import jolyjdia.connector.packet.PacketHandler;
import org.jetbrains.annotations.NotNull;

public class Initializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(@NotNull SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("decoder", new PacketDecoder());
        pipeline.addLast("encoder", new PacketEncoder());
        pipeline.addLast("handler", new NetworkManager(new PacketHandler()));
    }
}