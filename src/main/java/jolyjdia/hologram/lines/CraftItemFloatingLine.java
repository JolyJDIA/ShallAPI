package jolyjdia.hologram.lines;

import jolyjdia.api.craftentity.EntityEquip;
import jolyjdia.api.hologram.lines.ItemFloatingLine;
import jolyjdia.hologram.CraftHologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CraftItemFloatingLine extends CraftHoloLine implements ItemFloatingLine {

    private Material item;
    private float yaw;
    private boolean rotate;

    public CraftItemFloatingLine(CraftHologram hologram, boolean rotate, Location location, Material item) {
        super(hologram, location.clone());
        customStand.setSmall(false);
        this.rotate = rotate;
        setItem(item);
    }

    @Override
    public boolean isRotate() {
        return rotate;
    }

    @Override
    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }

    @Override
    public Material getItem(){
        return item;
    }

    @Override
    public void setItem(Material item){
        this.item = item;
        EntityEquip entityEquip = customStand.getEntityEquip();
        entityEquip.setHelmet(new ItemStack(item));
    }

    public void update(){
        if (!rotate)
            return;

        if (yaw >= 360){
            yaw = 0.0f;
        } else {
            customStand.setLook(yaw, 0.0f);
            yaw += 4.0f;
        }
    }

    public static double getItemFloatingEnter(Material item){
        if (item.isBlock())
            return 0.35;

        double enter = 0;
        switch (item) {
            case BOW:
                enter = 0.45;
                break;
            case LEATHER_HELMET:
            case CHAINMAIL_HELMET:
            case IRON_HELMET:
            case DIAMOND_HELMET:
                enter = 0.35;
                break;
            case LEATHER_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case IRON_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
                enter = 0.85;
                break;
            case LEATHER_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case IRON_LEGGINGS:
            case DIAMOND_LEGGINGS:
                enter = 1.4;
                break;
            case LEATHER_BOOTS:
            case CHAINMAIL_BOOTS:
            case IRON_BOOTS:
            case DIAMOND_BOOTS:
                enter = 1.85;
                break;
        }
        return enter;
    }
}
