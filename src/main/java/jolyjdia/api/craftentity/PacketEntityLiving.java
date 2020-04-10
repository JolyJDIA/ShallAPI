package jolyjdia.api.craftentity;

import jolyjdia.api.craft.PacketEntity;

public interface PacketEntityLiving extends PacketEntity {

    void setLook(float yaw, float pitch);

    /**
     * содержит все вещи, что сейчас одеты на энтити
     * @return - получить инвентарь жнтити
     */
    EntityEquip getEntityEquip();
}
