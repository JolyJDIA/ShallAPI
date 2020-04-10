package jolyjdia.nms.v1_15_R1.packet.entity;

import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.packet.entity.PacketEntityEquipment;
import net.minecraft.server.v1_15_R1.EnumItemSlot;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityEquipment;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PacketEntityEquipmentImpl extends DPacketEntityBase<PacketPlayOutEntityEquipment, DEntity> implements PacketEntityEquipment {

    private EquipmentSlot slot;
    private ItemStack itemStack;

    public PacketEntityEquipmentImpl(DEntity entity, EquipmentSlot slot, ItemStack itemStack) {
        super(entity);
        this.slot = slot;
        this.itemStack = itemStack;
    }

    @Override
    public void setSlot(EquipmentSlot slot) {
        this.slot = slot;
        init();
    }

    @Override
    public EquipmentSlot getSlot() {
        return slot;
    }

    @Override
    public DEntity getEntity() {
        return super.getEntity();
    }

    @Override
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        init();
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }
    @Override
    protected PacketPlayOutEntityEquipment init() {
        return new PacketPlayOutEntityEquipment(entity.getEntityID(), EnumItemSlot.valueOf(slot.name()),
                CraftItemStack.asNMSCopy(itemStack));
    }
}
