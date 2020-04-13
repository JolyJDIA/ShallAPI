package jolyjdia.netty.packets.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import jolyjdia.netty.packet.PacketBuffer;
import jolyjdia.netty.packet.ClientPacket;
import jolyjdia.netty.packets.ProtocolMap;

import java.util.UUID;

//Узнаю от сервера, что группа игрока изменилась
public class ClientUpdateGroupPacket implements ClientPacket {
    private UUID uuid;
    private int groupLvl;

    //читаю игрока и его новую группу
    @Override
    public void readPacketData(PacketBuffer buf) {
        uuid = buf.readUniqueId();
        groupLvl = buf.readInt();
    }

    @Override
    public void writePacketData(PacketBuffer buf) {
        buf.writeVarInt(ProtocolMap.getServerPacketId(this));
        buf.writeUniqueId(uuid);
        buf.writeInt(groupLvl);
    }

    //могу отсюда ее измененить
    @Override
    public void sendPacket(Channel channel) {
        //PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
        //writePacketData(buf);
        channel.writeAndFlush(this);
    }

    public int getGroupLvl() {
        return groupLvl;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setGroupLvl(int groupLvl) {
        this.groupLvl = groupLvl;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
