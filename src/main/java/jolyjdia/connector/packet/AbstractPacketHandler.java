package jolyjdia.connector.packet;

import jolyjdia.connector.packets.base.*;
import jolyjdia.connector.packets.stats.GamerStatsRequestPacket;
import jolyjdia.connector.packets.stats.GamerStatsResponsePacket;

public interface AbstractPacketHandler {
    void handle(GamerBaseRequestPacket baseDataPacket);
    void handle(GamerBaseResponsePacket baseDataPacket);

    void handle(GamerGroupResponsePacket baseDataPacket);
    void handle(GamerGroupRequestPacket baseDataPacket);

    void handle(GamerChangeGroupPacket groupPacket);

    void handle(GamerStatsRequestPacket statsDataPacket);
    void handle(GamerStatsResponsePacket statsDataPacket);

}