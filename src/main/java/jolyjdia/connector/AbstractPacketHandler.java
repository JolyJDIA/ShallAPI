package jolyjdia.connector;

import io.netty.channel.Channel;
import jolyjdia.connector.packets.base.*;
import jolyjdia.connector.packets.stats.GamerStatsRequestPacket;
import jolyjdia.connector.packets.stats.GamerStatsResponsePacket;

public interface AbstractPacketHandler {
    void handle(GamerBaseRequestPacket baseDataPacket, Channel channel);
    void handle(GamerBaseResponsePacket baseDataPacket, Channel channel);

    void handle(GamerGroupResponsePacket baseDataPacket, Channel channel);
    void handle(GamerGroupRequestPacket baseDataPacket, Channel channel);

    void handle(GamerChangeGroupPacket groupPacket, Channel channel);

    void handle(GamerStatsRequestPacket statsDataPacket, Channel channel);
    void handle(GamerStatsResponsePacket statsDataPacket, Channel channel);
}