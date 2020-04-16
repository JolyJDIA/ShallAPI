package jolyjdia.connector;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import jolyjdia.connector.codec.PacketEncoder;
import jolyjdia.connector.listener.UDPUpstreamHandler;
import org.jetbrains.annotations.NotNull;

public class Initializer extends ChannelInitializer<NioDatagramChannel> {

    @Override
    protected void initChannel(@NotNull NioDatagramChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        //pipeline.addLast("decoder", new PacketDecoder());
        pipeline.addLast("encoder", new PacketEncoder());
        pipeline.addLast("handler", new UDPUpstreamHandler());
    }
}