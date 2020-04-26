package jolyjdia.connector.packets;

import jolyjdia.connector.packet.ClientPacket;

import java.util.UUID;

public abstract class PlayerPacket implements ClientPacket {
    private UUID uuid;

    protected PlayerPacket() {}

    protected PlayerPacket(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
