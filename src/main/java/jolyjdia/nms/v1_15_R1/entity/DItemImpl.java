package jolyjdia.nms.v1_15_R1.entity;

import jolyjdia.nms.interfaces.entity.DEntityItem;
import net.minecraft.server.v1_15_R1.EntityItem;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.World;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class DItemImpl extends DEntityBase<EntityItem> implements DEntityItem {

    public DItemImpl(World world) {
        super(new EntityItem(EntityTypes.ITEM, world));
    }

    @Override
    public void setItemStack(ItemStack itemStack) {
        entity.setItemStack(CraftItemStack.asNMSCopy(itemStack));
    }
}
