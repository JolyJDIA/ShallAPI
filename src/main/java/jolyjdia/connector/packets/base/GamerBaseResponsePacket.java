package jolyjdia.connector.packets.base;

import io.netty.channel.Channel;
import jolyjdia.connector.AbstractPacketHandler;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.PlayerPacket;

public class GamerBaseResponsePacket extends PlayerPacket {
    private int playerId;
    private int groupLvl;
    private String skin;

    @Override
    public void readPacketData(PacketBuffer buf) {
        setUuid(buf.readUniqueId());
        this.playerId = buf.readInt();
        this.groupLvl = buf.readInt();
        this.skin = buf.readString(16);//ник в майне макс 16 символов
    }

    @Override
    public void writePacketData(PacketBuffer buf) {
        buf.writeVarInt(ProtocolMap.getServerPacketId(this));
        buf.writeUniqueId(getUuid());

        buf.writeInt(playerId);
        buf.writeInt(groupLvl);
        buf.writeString(skin);
    }

    @Override
    public void handle(AbstractPacketHandler handler, Channel channel) {
        handler.handle(this, channel);
    }

    public int getGroupLvl() {
        return groupLvl;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getSkin() {
        return skin;
    }
}
