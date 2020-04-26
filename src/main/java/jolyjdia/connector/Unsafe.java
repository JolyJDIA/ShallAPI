package jolyjdia.connector;

import jolyjdia.connector.packet.ClientPacket;

@FunctionalInterface
public interface Unsafe {
    void sendPacket(ClientPacket packet);
}
