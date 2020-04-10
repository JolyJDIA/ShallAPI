package jolyjdia.nms.interfaces.gui;

import jolyjdia.nms.types.EnchantingSlot;
import org.bukkit.inventory.ItemStack;

public interface DEnchantingTable extends DNmsGui {

    void addItem(EnchantingSlot slot, ItemStack stack);

    void setTitle(String title);
}
