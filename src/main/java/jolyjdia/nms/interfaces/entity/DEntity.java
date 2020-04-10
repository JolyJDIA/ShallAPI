package jolyjdia.nms.interfaces.entity;

import net.minecraft.server.v1_15_R1.EnumItemSlot;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface DEntity {
    //void spawnIn(World world);

    void setLocation(Location location);
    Location getLocation();

    int getEntityID();

    void setNoGravity(boolean gravity);

    boolean hasPassenger();
    void setPassenger(DEntity dEntity);
    void removePassenger();

    boolean getCustomNameVisible();
    void setCustomName(String name);
    void setCustomNameVisible(boolean visible);

    boolean isInvisible();

    boolean isOnGround();

    void watch(int type, byte value);

    UUID getUniqueID();

    EntityType getType();
    @Deprecated
    int getEntityTypeID();

    void setEquipment(EnumItemSlot equipment, ItemStack itemStack);

    void setGlowing(boolean glowing);

    Entity getBukkitEntity();
}
