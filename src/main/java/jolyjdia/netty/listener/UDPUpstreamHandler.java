package jolyjdia.netty.listener;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import jolyjdia.Main;
import jolyjdia.MainEvents;
import jolyjdia.api.boards.PlayerTag;
import jolyjdia.api.permission.Group;
import jolyjdia.api.permission.PermissionManager;
import jolyjdia.api.skin.SkinAPI;
import jolyjdia.netty.packet.ClientPacket;
import jolyjdia.netty.packet.PacketBuffer;
import jolyjdia.netty.packet.ServerPacket;
import jolyjdia.netty.packets.Packets;
import jolyjdia.netty.packets.client.ClientAutoMessagesPacket;
import jolyjdia.netty.packets.client.ClientGetGroupPacket;
import jolyjdia.netty.packets.server.ServerAutoMessagesPacket;
import jolyjdia.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class UDPUpstreamHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext context, @NotNull DatagramPacket datagramPacket) {
        PacketBuffer buffer = new PacketBuffer(datagramPacket.content());
        ServerPacket packet = Packets.getPacketServer(buffer.readVarInt());

        if(packet == null) {
            return;
        }
        packet.readPacketData(buffer);
        if(packet instanceof ClientGetGroupPacket) {
            ClientGetGroupPacket groupPacket = (ClientGetGroupPacket)packet;
            Player p = Bukkit.getPlayer(groupPacket.getUuid());
            if(p == null || !p.isOnline()) {
                return;
            }
            Group group = PermissionManager.getGroup(groupPacket.getGroupLvl());
            Bukkit.broadcastMessage(' ' +group.getPrefix()+ ' ' +p.getName()+" §fзашел на сервер");
            SkinAPI.getSkinAsync("Jotaro_Kujo").thenAccept(s -> Main.NMS_API.setSkin(p, s.getValue(), s.getSignature()));
            //Main.NMS_API.setSkin(p, Skin.DEFAULT.getValue(), Skin.DEFAULT.getSignature());
            @NonNls PlayerTag playerTag = MainEvents.SCORE_BOARD_API.createTag(
                    MainEvents.SCORE_BOARD_API.getPriorityScoreboardTag(group) + p.getName()//хуйня
            );
            playerTag.addPlayerToTeam(p);
            playerTag.setPrefix(group.getPrefix() + ' ');
            playerTag.disableCollidesForAll();
            MainEvents.SCORE_BOARD_API.setDefaultTag(p, playerTag);
        } else if(packet instanceof ClientAutoMessagesPacket) {
            ClientAutoMessagesPacket messagesPacket = (ClientAutoMessagesPacket)packet;
            BukkitUtils.messagesPlayers(messagesPacket.getText());
        }
    }
}
