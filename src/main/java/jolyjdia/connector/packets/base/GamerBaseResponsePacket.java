package jolyjdia.connector.packets.base;

import jolyjdia.connector.packet.AbstractPacketHandler;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.PlayerPacket;
import org.jetbrains.annotations.NotNull;

public class GamerBaseResponsePacket extends PlayerPacket {
    private static final int PACKET_ID = ProtocolMap.getServerPacketId(GamerBaseResponsePacket.class);
    private int playerId;
    private int groupLvl;
    private String skin;

    @Override
    public void readPacketData(@NotNull PacketBuffer buf) {
        setUuid(buf.readUniqueId());
        this.playerId = buf.readInt();
        this.groupLvl = buf.readInt();
        this.skin = buf.readString(16);//ник в майне макс 16 символов
    }

    @Override
    public void writePacketData(@NotNull PacketBuffer buf) {
        buf.writeVarInt(PACKET_ID);
        buf.writeUniqueId(getUuid());

        buf.writeInt(playerId);
        buf.writeInt(groupLvl);
        buf.writeString(skin);
    }

    @Override
    public void handle(@NotNull AbstractPacketHandler handler) {
        handler.handle(this);
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
