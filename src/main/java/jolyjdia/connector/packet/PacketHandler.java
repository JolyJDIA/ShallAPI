package jolyjdia.connector.packet;

import jolyjdia.Main;
import jolyjdia.api.AccountAPI;
import jolyjdia.api.constant.GroupImpl;
import jolyjdia.api.events.gamer.GamerJoinEvent;
import jolyjdia.api.events.gamer.GamerStatsEvent;
import jolyjdia.api.events.gamer.GamerUpdateGroupEvent;
import jolyjdia.api.player.GamePlayer;
import jolyjdia.connector.packets.base.*;
import jolyjdia.connector.packets.stats.GamerStatsRequestPacket;
import jolyjdia.connector.packets.stats.GamerStatsResponsePacket;
import jolyjdia.utils.BukkitUtils;
import org.jetbrains.annotations.NotNull;

public class PacketHandler implements AbstractPacketHandler {
    @Override
    public void handle(@NotNull GamerBaseRequestPacket baseDataPacket) {
        sendPacket(baseDataPacket);
    }

    @Override
    public void handle(GamerChangeGroupPacket groupPacket) {
        sendPacket(groupPacket);
    }

    @Override
    public void handle(@NotNull GamerStatsRequestPacket statsDataPacket) {
        sendPacket(statsDataPacket);
    }

    @Override
    public void handle(@NotNull GamerGroupRequestPacket baseDataPacket) {
        sendPacket(baseDataPacket);
    }

    @Override
    public void handle(@NotNull GamerStatsResponsePacket statsPacket) {
        AccountAPI.getIfLoaded(statsPacket.getUuid()).ifPresent(e -> {
            e.addExp(statsPacket.getExp());
            e.addKeys(statsPacket.getKeys());
            e.addLevel(statsPacket.getLvl());
            e.addMoney(statsPacket.getMoney());
            System.out.println("sdadasdasdasd");
            BukkitUtils.callSyncEvent(new GamerStatsEvent(e));
        });
    }

    @Override
    public void handle(@NotNull GamerBaseResponsePacket baseDataPacket) {
        GamePlayer gamePlayer = AccountAPI.loadGamerIfAbsentOrGet(
                baseDataPacket.getPlayerId(),
                baseDataPacket.getUuid()
        );
        GroupImpl.getGroupByLvl(baseDataPacket.getGroupLvl()).ifPresent(gamePlayer::setGroup);
        BukkitUtils.callSyncEvent(new GamerJoinEvent(gamePlayer));
    }

    @Override
    public void handle(@NotNull GamerGroupResponsePacket groupPacket) {
        AccountAPI.getIfLoaded(groupPacket.getUuid()).ifPresent(e -> {
            GroupImpl.getGroupByLvl(groupPacket.getGroupLvl()).ifPresent(e::setGroup);
            BukkitUtils.callSyncEvent(new GamerUpdateGroupEvent(e));
        });
    }
    private static void sendPacket(ClientPacket packet) {
        Main.getInstance().getChannel().writeAndFlush(packet);
    }
}
