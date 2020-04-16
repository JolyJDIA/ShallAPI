package jolyjdia.connector.packets.client;

import jolyjdia.connector.packet.ClientPacket;

import java.util.UUID;

public abstract class PlayerPacket implements ClientPacket {
    private UUID uuid;

    PlayerPacket() {}

    PlayerPacket(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
