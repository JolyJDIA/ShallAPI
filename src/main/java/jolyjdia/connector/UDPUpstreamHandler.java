package jolyjdia.connector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import jolyjdia.api.AccountAPI;
import jolyjdia.api.constant.GroupImpl;
import jolyjdia.api.events.gamer.AsyncGamerJoinEvent;
import jolyjdia.api.events.gamer.GamerJoinEvent;
import jolyjdia.api.events.gamer.GamerUpdateGroupEvent;
import jolyjdia.api.player.GamePlayer;
import jolyjdia.connector.packet.ClientPacket;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packet.ProtocolMap;
import jolyjdia.connector.packets.ClientAutoMessagesPacket;
import jolyjdia.connector.packets.ClientGetBaseDataPacket;
import jolyjdia.connector.packets.ClientUpdateGroupPacket;
import jolyjdia.connector.packets.PlayerPacket;
import jolyjdia.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UDPUpstreamHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext context, @NotNull DatagramPacket datagramPacket) {
        PacketBuffer buffer = new PacketBuffer(datagramPacket.content());
        ClientPacket packet = ProtocolMap.getPacketServer(buffer.readVarInt());

        if(packet == null) {
            return;
        }
        packet.readPacketData(buffer);
        if(packet instanceof PlayerPacket) {
            Player p = Bukkit.getPlayer(((PlayerPacket) packet).getUuid());
            if (p == null || !p.isOnline()) {
                return;
            }
            if (packet instanceof ClientGetBaseDataPacket) {//todo: switch
                ClientGetBaseDataPacket baseDataPacket = (ClientGetBaseDataPacket) packet;

                System.out.println(baseDataPacket.getGroupLvl());
                GamePlayer gamePlayer = AccountAPI.loadGamerIfAbsentOrGet(
                        baseDataPacket.getPlayerId(),
                        baseDataPacket.getUuid()
                );
                System.out.println(baseDataPacket.getGroupLvl());
                GroupImpl.getGroupByLvl(baseDataPacket.getGroupLvl()).ifPresent(gamePlayer::setGroup);
                BukkitUtils.callSyncEvent(new GamerJoinEvent(gamePlayer));
                Bukkit.getPluginManager().callEvent(new AsyncGamerJoinEvent(gamePlayer));
            } else if (packet instanceof ClientUpdateGroupPacket) {
                ClientUpdateGroupPacket groupPacket = (ClientUpdateGroupPacket) packet;
                AccountAPI.getIfLoaded(groupPacket.getUuid()).ifPresent(e -> {
                    GroupImpl.getGroupByLvl(groupPacket.getGroupLvl()).ifPresent(e::setGroup);
                    BukkitUtils.callSyncEvent(new GamerUpdateGroupEvent(e));
                });
            }
        } else if (packet instanceof ClientAutoMessagesPacket) {
            ClientAutoMessagesPacket messagesPacket = (ClientAutoMessagesPacket) packet;
            BukkitUtils.messagesPlayers(messagesPacket.getText());
        }
    }
}
