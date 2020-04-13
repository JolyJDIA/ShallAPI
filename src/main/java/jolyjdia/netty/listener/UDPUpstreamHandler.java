package jolyjdia.netty.listener;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import jolyjdia.MainEvents;
import jolyjdia.api.AccountAPI;
import jolyjdia.api.boards.PlayerTag;
import jolyjdia.api.constant.GroupImp;
import jolyjdia.api.player.GamePlayer;
import jolyjdia.netty.packet.PacketBuffer;
import jolyjdia.netty.packet.ClientPacket;
import jolyjdia.netty.packets.ProtocolMap;
import jolyjdia.netty.packets.client.ClientAutoMessagesPacket;
import jolyjdia.netty.packets.client.ClientGetGroupPacket;
import jolyjdia.netty.packets.client.ClientUpdateGroupPacket;
import jolyjdia.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NonNls;
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
        if(packet instanceof ClientGetGroupPacket) {
            ClientGetGroupPacket groupPacket = (ClientGetGroupPacket)packet;
            GamePlayer gamePlayer = AccountAPI.loadGamerIfAbsentOrGet(groupPacket.getUuid());
            GroupImp group = GroupImp.getGroupByLvl(groupPacket.getGroupLvl());
            gamePlayer.setGroup(group);

            Player p = Bukkit.getPlayer(groupPacket.getUuid());
            if(p == null || !p.isOnline()) {
                return;
            }

            Bukkit.broadcastMessage(' ' +group.getPrefix()+ ' ' +p.getName()+" §fзашел на сервер");
        //    Main.NMS_API.setSkin(p, Skin.DEFAULT.getValue(), Skin.DEFAULT.getSignature());

            @NonNls PlayerTag playerTag = MainEvents.SCORE_BOARD_API.createTag(
                    group.getStar() + p.getName()//хуйня
            );
            playerTag.addPlayerToTeam(p);
            playerTag.setPrefix(group.getPrefix() + ' ');
            playerTag.disableCollidesForAll();
            MainEvents.SCORE_BOARD_API.setDefaultTag(p, playerTag);
        } else if(packet instanceof ClientAutoMessagesPacket) {
            ClientAutoMessagesPacket messagesPacket = (ClientAutoMessagesPacket)packet;
            BukkitUtils.messagesPlayers(messagesPacket.getText());
        } else if(packet instanceof ClientUpdateGroupPacket) {
            ClientUpdateGroupPacket groupPacket = (ClientUpdateGroupPacket)packet;
            GamePlayer gamePlayer = AccountAPI.loadGamerIfAbsentOrGet(groupPacket.getUuid());
            GroupImp group = GroupImp.getGroupByLvl(groupPacket.getGroupLvl());
            gamePlayer.setGroup(group);
        }
    }
}
