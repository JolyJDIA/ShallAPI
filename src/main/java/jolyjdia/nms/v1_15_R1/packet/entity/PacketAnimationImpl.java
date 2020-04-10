package jolyjdia.nms.v1_15_R1.packet.entity;

import jolyjdia.api.craftentity.npc.AnimationNpcType;
import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.packet.entity.PacketAnimation;
import jolyjdia.nms.v1_15_R1.entity.DEntityBase;
import net.minecraft.server.v1_15_R1.PacketPlayOutAnimation;

public class PacketAnimationImpl extends DPacketEntityBase<PacketPlayOutAnimation, DEntity> implements PacketAnimation {

    private AnimationNpcType animation;

    public PacketAnimationImpl(DEntity entity, AnimationNpcType animation) {
        super(entity);
        this.animation = animation;
    }

    @Override
    public void setAnimation(AnimationNpcType animation) {
        this.animation = animation;
        init();
    }
    @Override
    public AnimationNpcType getAnimation() {
        return animation;
    }

    @Override
    protected PacketPlayOutAnimation init() {
        return new PacketPlayOutAnimation(((DEntityBase)entity).getEntityNms(), animation.ordinal());
    }

}
