package jolyjdia.api.craftentity;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;

public interface EntityEquip {
    @Nullable
    ItemStack getHelmet();
    @Nullable
    ItemStack getChestplate();
    @Nullable
    ItemStack getLeggings();
    @Nullable
    ItemStack getBoots();
    @Nullable
    ItemStack getItemInMainHand();
    @Nullable
    ItemStack getItemInOffHand();

    @Nullable
    ItemStack getItem(EquipmentSlot equipType);

    void setItem(EquipmentSlot equipType, ItemStack itemStack);

    void setHelmet(ItemStack helmet);
    void setChestplate(ItemStack chestplate);
    void setLeggings(ItemStack leggings);
    void setBoots(ItemStack boots);
    void setItemInMainHand(ItemStack item);
    void setItemInOffHand(ItemStack item);

    boolean removeItem(EquipmentSlot equipType);
    void removeItems();

    /**
     * список того, что одето на энтити
     * @return - список
     */
    Map<EquipmentSlot, ItemStack> getItemsEquip();//todo EnumMap
}
