package jolyjdia.nms.interfaces.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public interface PacketEntityEquipment extends DPacketEntity<DEntity> {

    void setSlot(EquipmentSlot slot);

    EquipmentSlot getSlot();

    void setItemStack(ItemStack itemStack);

    ItemStack getItemStack();
}
