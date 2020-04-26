package jolyjdia.connector.packets.stats;

import io.netty.channel.Channel;
import jolyjdia.connector.AbstractPacketHandler;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.PlayerPacket;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GamerStatsResponsePacket extends PlayerPacket {
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
        buf.writeVarInt(ProtocolMap.getServerPacketId(this));
        buf.writeUniqueId(getUuid());

        buf.writeInt(money);
        buf.writeInt(lvl);
        buf.writeInt(exp);
        buf.writeInt(keys);
    }

    @Override
    public void handle(AbstractPacketHandler handler, Channel channel) {
        handler.handle(this, channel);
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
