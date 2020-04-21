package jolyjdia.connector.packets;

import io.netty.channel.Channel;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

//Узнаю от сервера, что группа игрока изменилась
public class ClientUpdateGroupPacket extends PlayerPacket {
    private int groupLvl;

    public ClientUpdateGroupPacket() {}

    public ClientUpdateGroupPacket(UUID uuid, int groupLvl) {
        super(uuid);
        this.groupLvl = groupLvl;
    }

    //читаю игрока и его новую группу
    @Override
    public void readPacketData(@NotNull PacketBuffer buf) {
        setUuid(buf.readUniqueId());
        groupLvl = buf.readInt();
    }

    @Override
    public void writePacketData(@NotNull PacketBuffer buf) {
        buf.writeVarInt(ProtocolMap.getServerPacketId(this));
        buf.writeUniqueId(getUuid());
        buf.writeInt(groupLvl);
    }

    //могу отсюда ее измененить
    @Override
    public void sendPacket(@NotNull Channel channel) {
        channel.writeAndFlush(this);
    }

    public int getGroupLvl() {
        return groupLvl;
    }

    public void setGroupLvl(int groupLvl) {
        this.groupLvl = groupLvl;
    }
}
