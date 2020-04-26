package jolyjdia.connector.packets.stats;

import jolyjdia.connector.packet.AbstractPacketHandler;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.PlayerPacket;
import org.jetbrains.annotations.NotNull;

public class GamerStatsResponsePacket extends PlayerPacket {
    private static final int PACKET_ID = ProtocolMap.getServerPacketId(GamerStatsResponsePacket.class);
    private int money, lvl, exp, keys;

    @Override
    public void readPacketData(@NotNull PacketBuffer buf) {
        setUuid(buf.readUniqueId());
        this.money = buf.readInt();
        this.lvl = buf.readInt();
        this.exp = buf.readInt();
        this.keys = buf.readInt();

    }

    @Override
    public void writePacketData(@NotNull PacketBuffer buf) {
        buf.writeVarInt(PACKET_ID);
        buf.writeUniqueId(getUuid());

        buf.writeInt(money);
        buf.writeInt(lvl);
        buf.writeInt(exp);
        buf.writeInt(keys);
    }

    @Override
    public void handle(@NotNull AbstractPacketHandler handler) {
        handler.handle(this);
    }

    public int getKeys() {
        return keys;
    }

    public int getExp() {
        return exp;
    }

    public int getLvl() {
        return lvl;
    }

    public int getMoney() {
        return money;
    }
}
