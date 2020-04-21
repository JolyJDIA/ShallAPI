package jolyjdia.connector.packets;

import io.netty.channel.Channel;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * ДЛЯ ОТПРАВКИ НА СЕРВЕРЕ
 */
public class ClientGetBaseDataPacket extends PlayerPacket {
    private int playerId;
    private int groupLvl;
    private String skin;

    public ClientGetBaseDataPacket() {}

    public ClientGetBaseDataPacket(UUID uuid) {
        super(uuid);
    }

    //получаю данные
    @Override
    public void readPacketData(@NotNull PacketBuffer buf) {
        setUuid(buf.readUniqueId());
        playerId = buf.readInt();
        groupLvl = buf.readInt();
        skin = buf.readString(16);//In Java Edition, usernames must be 3–16 characters
    }

    @Override
    public void writePacketData(@NotNull PacketBuffer buf) {
        buf.writeVarInt(ProtocolMap.getServerPacketId(this));
        buf.writeUniqueId(getUuid());
    }

    @Override
    public void sendPacket(@NotNull Channel channel) {
        channel.writeAndFlush(this);
    }

    public int getGroupLvl() {
        return groupLvl;
    }

    public String getSkin() {
        return skin;
    }

    public int getPlayerId() {
        return playerId;
    }
}