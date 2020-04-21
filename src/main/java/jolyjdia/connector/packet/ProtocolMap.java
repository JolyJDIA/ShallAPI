package jolyjdia.connector.packet;

import com.google.common.collect.ImmutableBiMap;
import jolyjdia.connector.packets.ClientAutoMessagesPacket;
import jolyjdia.connector.packets.ClientGetBaseDataPacket;
import jolyjdia.connector.packets.ClientUpdateGroupPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ProtocolMap {
    public static final ImmutableBiMap<Integer, Class<? extends ClientPacket>> PACKETS =
            ImmutableBiMap.<Integer, Class<? extends ClientPacket>>builder()
            .put(0, ClientGetBaseDataPacket.class)
            .put(1, ClientUpdateGroupPacket.class)
            .put(2, ClientAutoMessagesPacket.class)
            .build();

    private ProtocolMap() {}

    public static int getServerPacketId(@NotNull ClientPacket packet) {
        return PACKETS.inverse().get(packet.getClass());
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
