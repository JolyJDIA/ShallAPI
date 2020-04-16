package jolyjdia.connector.listener;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import jolyjdia.Main;
import jolyjdia.MainEvents;
import jolyjdia.api.AccountAPI;
import jolyjdia.api.boards.PlayerTag;
import jolyjdia.api.constant.GroupImp;
import jolyjdia.api.player.GamePlayer;
import jolyjdia.api.skin.SkinAPI;
import jolyjdia.connector.packet.ClientPacket;
import jolyjdia.connector.packet.PacketBuffer;
import jolyjdia.connector.packets.ProtocolMap;
import jolyjdia.connector.packets.client.ClientAutoMessagesPacket;
import jolyjdia.connector.packets.client.ClientGetBaseDataPacket;
import jolyjdia.connector.packets.client.ClientUpdateGroupPacket;
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
        if(packet instanceof ClientGetBaseDataPacket) {
            //ИГРОК
            ClientGetBaseDataPacket baseDataPacket = (ClientGetBaseDataPacket)packet;
            GamePlayer gamePlayer = AccountAPI.loadGamerIfAbsentOrGet(baseDataPacket.getUuid());
            Player p = Bukkit.getPlayer(baseDataPacket.getUuid());
            if(p == null || !p.isOnline()) {
                return;
            }

            //ГРУППА
            GroupImp group = GroupImp.getGroupByLvl(baseDataPacket.getGroupLvl());
            gamePlayer.setGroup(group);

            //ДЖОИН МЕССЕДЖ
            Bukkit.broadcastMessage(' ' +group.getPrefix()+ ' ' +p.getName()+" §fзашел на сервер");

            //СКИН
            System.out.println(baseDataPacket.getSkin());
            SkinAPI.getSkinAsync(baseDataPacket.getSkin()).thenAccept(e -> Main.NMS_API.setSkin(p, e));

            //TAG
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
