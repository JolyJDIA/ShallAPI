package jolyjdia.netty.packet;

public interface Packet {
    void readPacketData(PacketBuffer buf);

    void writePacketData(PacketBuffer buf);
}