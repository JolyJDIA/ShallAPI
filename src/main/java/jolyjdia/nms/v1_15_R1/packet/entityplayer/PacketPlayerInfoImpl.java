package jolyjdia.nms.v1_15_R1.packet.entityplayer;

import jolyjdia.nms.interfaces.entity.DEntityPlayer;
import jolyjdia.nms.interfaces.packet.entityplayer.PacketPlayerInfo;
import jolyjdia.nms.types.PlayerInfoActionType;
import jolyjdia.nms.v1_15_R1.entity.DEntityLivingBase;
import jolyjdia.nms.v1_15_R1.packet.entity.DPacketEntityBase;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo;

public class PacketPlayerInfoImpl extends DPacketEntityBase<PacketPlayOutPlayerInfo, DEntityPlayer>
        implements PacketPlayerInfo {

    private PlayerInfoActionType actionType;

    public PacketPlayerInfoImpl(DEntityPlayer entity, PlayerInfoActionType actionType) {
        super(entity);
        this.actionType = actionType;
    }

    @Override
    protected PacketPlayOutPlayerInfo init() {
        return new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.valueOf(actionType.name()),
                ((DEntityLivingBase<EntityPlayer>) entity).getEntityNms());
    }

    @Override
    public void setPlayerInfoAction(PlayerInfoActionType actionType) {
        this.actionType = actionType;
        init();
    }

    @Override
    public PlayerInfoActionType getActionType() {
        return actionType;
    }
}
