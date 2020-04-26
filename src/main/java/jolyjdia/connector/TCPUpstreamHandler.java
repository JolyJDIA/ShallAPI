package jolyjdia.connector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import jolyjdia.api.AccountAPI;
import jolyjdia.api.constant.GroupImpl;
import jolyjdia.api.events.gamer.AsyncGamerJoinEvent;
import jolyjdia.api.events.gamer.GamerJoinEvent;
import jolyjdia.api.events.gamer.GamerStatsEvent;
import jolyjdia.api.events.gamer.GamerUpdateGroupEvent;
import jolyjdia.api.player.GamePlayer;
import jolyjdia.connector.packet.ClientPacket;
import jolyjdia.connector.packets.*;
import jolyjdia.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TCPUpstreamHandler extends SimpleChannelInboundHandler<ClientPacket> {

    private final AbstractPacketHandler handler;

    public TCPUpstreamHandler(AbstractPacketHandler handler) {
        this.handler = handler;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext context, @NotNull ClientPacket packet) {
        System.out.println(packet);
        packet.handle(handler, context.channel());
    }
}
