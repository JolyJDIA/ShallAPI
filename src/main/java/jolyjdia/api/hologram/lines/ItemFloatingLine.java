package jolyjdia.api.hologram.lines;

import org.bukkit.Material;

public interface ItemFloatingLine {

    void setRotate(boolean rotate);

    boolean isRotate();

    Material getItem();

    void setItem(Material item);
}
