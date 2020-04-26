package jolyjdia.connector.packet;

import com.google.common.collect.ImmutableBiMap;
import jolyjdia.connector.packets.base.*;
import jolyjdia.connector.packets.stats.GamerStatsRequestPacket;
import jolyjdia.connector.packets.stats.GamerStatsResponsePacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ProtocolMap {
    public static final ImmutableBiMap<Integer, Class<? extends ClientPacket>> PACKETS =
            ImmutableBiMap.<Integer, Class<? extends ClientPacket>>builder()
            .put(0, GamerBaseRequestPacket.class)
            .put(1, GamerBaseResponsePacket.class)

            .put(2, GamerGroupRequestPacket.class)
            .put(3, GamerGroupResponsePacket.class)
            .put(4, GamerChangeGroupPacket.class)

            .put(5, GamerStatsRequestPacket.class)
            .put(6, GamerStatsResponsePacket.class)
            .build();

    private ProtocolMap() {}

    public static int getServerPacketId(@NotNull ClientPacket packet) {
        return PACKETS.inverse().get(packet.getClass());
    }
    public static int getServerPacketId(Class<? extends ClientPacket> packetClz) {
        return PACKETS.inverse().get(packetClz);
    }
    public static @Nullable ClientPacket getPacketServer(int id) {//Инстанс обработчика по ключевому слову
        try {
            return PACKETS.get(id).getConstructor().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
