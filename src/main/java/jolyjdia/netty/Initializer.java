package jolyjdia.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import jolyjdia.netty.codec.PacketDecoder;
import jolyjdia.netty.codec.PacketEncoder;
import jolyjdia.netty.listener.UDPUpstreamHandler;
import org.jetbrains.annotations.NotNull;

public class Initializer extends ChannelInitializer<NioDatagramChannel> {

    @Override
    protected void initChannel(@NotNull NioDatagramChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("decoder", new PacketDecoder());
        pipeline.addLast("encoder", new PacketEncoder());
        pipeline.addLast("handler", new UDPUpstreamHandler());
    }
}