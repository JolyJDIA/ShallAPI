package jolyjdia.nms.interfaces.packet.entity;

import jolyjdia.api.craftentity.npc.AnimationNpcType;
import jolyjdia.nms.interfaces.entity.DEntity;

public interface PacketAnimation extends DPacketEntity<DEntity> {

    AnimationNpcType getAnimation();

    void setAnimation(AnimationNpcType animation);
}
