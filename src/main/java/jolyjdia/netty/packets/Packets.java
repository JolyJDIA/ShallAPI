package jolyjdia.netty.packets;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.google.common.collect.ImmutableList;
import jolyjdia.netty.packet.ClientPacket;
import jolyjdia.netty.packet.ServerPacket;
import jolyjdia.netty.packets.client.ClientAutoMessagesPacket;
import jolyjdia.netty.packets.client.ClientGetGroupPacket;
import jolyjdia.netty.packets.client.ClientUpdateGroupPacket;
import jolyjdia.netty.packets.server.ServerAutoMessagesPacket;
import jolyjdia.netty.packets.server.ServerGetGroup;
import jolyjdia.netty.packets.server.ServerUpdateGroup;
import jolyjdia.utils.Pair;
import org.jetbrains.annotations.Nullable;

public final class Packets {
    private static final
    ImmutableList<Pair<Class<? extends ClientPacket>, Class<? extends ServerPacket>>> PACKETS = ImmutableList.of(
            Pair.put(ServerGetGroup.class, ClientGetGroupPacket.class),
            Pair.put(ServerUpdateGroup.class, ClientUpdateGroupPacket.class),
            Pair.put(ServerAutoMessagesPacket.class, ClientAutoMessagesPacket.class)
    );

    private Packets() {}

    public static int getClientPacketId(ClientPacket packet) {
        int id = 0;
        for(Pair<Class<? extends ClientPacket>, Class<? extends ServerPacket>> pair : PACKETS) {
            if(pair.getFirst() == packet.getClass()) {
                return id;
            }
            ++id;
        }
        throw new NullPointerException();
    }
    public static int getServerPacketId(ServerPacket packet) {
        int id = 0;
        for(Pair<Class<? extends ClientPacket>, Class<? extends ServerPacket>> pair : PACKETS) {
            if(pair.getSecond() == packet.getClass()) {
                return id;
            }
            ++id;
        }
        throw new NullPointerException();
    }
    public static @Nullable ServerPacket getPacketServer(int id) {//Инстанс обработчика по ключевому слову
        try {
            return PACKETS.get(id).getSecond().getConstructor().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static @Nullable ClientPacket getPacketClient(int id) {//Инстанс обработчика по ключевому слову
        try {
            return PACKETS.get(id).getFirst().getConstructor().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
