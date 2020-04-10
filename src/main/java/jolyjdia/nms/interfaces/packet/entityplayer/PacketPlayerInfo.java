package jolyjdia.nms.interfaces.packet.entityplayer;

import jolyjdia.nms.interfaces.entity.DEntityPlayer;
import jolyjdia.nms.interfaces.packet.entity.DPacketEntity;
import jolyjdia.nms.types.PlayerInfoActionType;

public interface PacketPlayerInfo extends DPacketEntity<DEntityPlayer> {

    void setPlayerInfoAction(PlayerInfoActionType actionType);

    PlayerInfoActionType getActionType();
}
