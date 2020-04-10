package jolyjdia.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

public class CustomItem {
    private ItemStack stack;
    private ItemMeta meta;

    public final CustomItem createItem(Material material) {
        this.stack = new ItemStack(material);
        this.meta = stack.getItemMeta();
        return this;
    }

    public final CustomItem setName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public final CustomItem setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public final CustomItem setAmount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    public final CustomItem setEnchants(@NotNull Map<Enchantment, Integer> enchant) {
        for (Map.Entry<Enchantment, Integer> entry : enchant.entrySet()) {
            meta.addEnchant(entry.getKey(), entry.getValue(), true);
        }
        return this;
    }

    public final ItemStack build() {
        stack.setItemMeta(meta);
        return stack;
    }
}