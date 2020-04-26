package jolyjdia.connector.packet;

public interface ClientPacket {
    void readPacketData(PacketBuffer buf);
    void writePacketData(PacketBuffer buf);
    void handle(AbstractPacketHandler handler);
}
