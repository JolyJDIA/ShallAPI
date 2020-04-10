package jolyjdia.api.hologram.lines;

import org.bukkit.Material;

public interface ItemDropLine {

    Material getItemStack();

    void setItem(Material item);

    boolean isPickup();

    void setPickup(boolean pickup);
}
