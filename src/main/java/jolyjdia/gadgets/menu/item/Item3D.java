package jolyjdia.gadgets.menu.item;

import jolyjdia.gadgets.nms.entity.ReflectedArmorStand;
import jolyjdia.gadgets.nms.entity.ReflectedLivingEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Item3D {
    private Player player;
    private Location location;
    private ReflectedArmorStand itemDisplay;
    private ReflectedArmorStand displayName;
    private ReflectedLivingEntity selector;
    private String name;
    private ItemStack item;
    private Location dir;
    private boolean spawned;
    private final boolean enable;
    private float yawRotation;

    public Item3D(Player p, ItemStack stack, String s) {
        this.player = p;
        this.item = stack;
        this.name = s;
        this.enable = true;
        this.dir = p.getLocation().toVector().add(p.getLocation().getDirection().multiply(1.4f)).toLocation(p.getWorld(), p.getLocation().getYaw(), 0.0F);
        this.dir.subtract(0.0f, 1.5f, 0.0f);
        this.displayName = (new ReflectedArmorStand(this.dir.clone().add(0.0f, 0.3f, 0.0f)));
        displayName.setVisible(false);
        displayName.setDisplayName(s);

        this.itemDisplay = (new ReflectedArmorStand(this.dir));
        itemDisplay.setEquipment(5, stack);
        itemDisplay.setVisible(false);

    }

    public Item3D(Player p, ItemStack stack) {
        this.player = p;
        this.item = stack;
        this.name = stack.getItemMeta().getDisplayName();
        this.enable = true;
        this.dir = p.getLocation().toVector().add(p.getLocation().getDirection().multiply(1.4f)).toLocation(p.getWorld(), p.getLocation().getYaw(), 0.0F);
        this.dir.subtract(0.0f, 1.5f, 0.0f);
        this.displayName = (new ReflectedArmorStand(this.dir.clone().add(0.0f, 0.3f, 0.0f)));
        displayName.setVisible(false);
        displayName.setDisplayName(name);
        this.itemDisplay = (new ReflectedArmorStand(this.dir));
        itemDisplay.setVisible(false);
        itemDisplay.setEquipment(5, stack);
    }
    public Item3D(Player p) {
        this.player = p;
        this.enable = true;
        this.dir = p.getLocation().toVector().add(p.getLocation().getDirection().multiply(1.4f)).toLocation(p.getWorld(), p.getLocation().getYaw(), 0.0F);
        this.dir.subtract(0.0f, 1.5f, 0.0f);
        this.displayName = (new ReflectedArmorStand(this.dir.clone().add(0.0f, 0.3f, 0.0f)));
        displayName.setVisible(false);
        //    displayName.setDisplayName(name);
        this.itemDisplay = (new ReflectedArmorStand(this.dir));
        itemDisplay.setVisible(false);
    }
    public Item3D(ItemStack stack, String s) {
        this.item = stack;
        this.name = stack.getItemMeta().getDisplayName();
        this.enable = true;
        this.name = s;
    }
    public final void setPlayerX(Player p) {
        player = p;
        this.dir = p.getLocation().toVector().add(p.getLocation().getDirection().multiply(1.4f)).toLocation(p.getWorld(), p.getLocation().getYaw(), 0.0F);
        this.dir.subtract(0.0f, 1.5f, 0.0f);
        this.displayName = (new ReflectedArmorStand(this.dir.clone().add(0.0f, 0.3f, 0.0f)));
        displayName.setVisible(false);
        displayName.setDisplayName(name);
        this.itemDisplay = (new ReflectedArmorStand(this.dir));
        itemDisplay.setVisible(false);
        itemDisplay.setEquipment(5, item);
    }

    public final void setPlayer(Player p) {
        player = p;
    }

    public final void setItem(ItemStack stack) {
        item = stack;
    }

    public final void setSmall() {
        this.itemDisplay.setSmall(true);
    }

    public final void setPosition(Location loc, float v1) {
        Vector v = null;
        this.itemDisplay.setRotation(v1, 0.0f);
        this.yawRotation = v1;
        Location lloc;
        if (!this.item.getType().isBlock() && this.item.getType() != Material.SKELETON_SKULL) {
            lloc = loc.clone();
            lloc.setDirection(this.player.getLocation().toVector().subtract(lloc.toVector()).normalize());
            lloc.setPitch(0.0F);
            v = lloc.getDirection().multiply(0.3f);
        } else {
            this.itemDisplay.setSmall(true);
        }

        this.itemDisplay.spawnArmorStand(this.player);
        this.displayName.spawnArmorStand(this.player);
        lloc = this.itemDisplay.isSmall() ? loc.clone().add(0.0f, 1.3f, 0.0f) : loc;
        if (this.item.getType() == Material.LEGACY_SAPLING) {
            lloc.subtract(0.0f, 0.3f, 0.0f);
        } else if (this.item.getType() == Material.BARRIER) {
            lloc.subtract(0.0f, 0.2f, 0.0f);
        }

        this.itemDisplay.teleport(this.player, v != null ? lloc.clone().add(v) : lloc);
        this.displayName.teleport(this.player, loc.clone().add(0.0f, 0.3f, 0.0f));
        this.selector = new ReflectedLivingEntity(loc.clone().add(0.0f, 2.0f, 0.0f), EntityType.SLIME);//SIZE 2 / 1.8f
        this.selector.setSize(1);
        this.selector.setVisible(false);
        this.selector.setYawRotation(v1);
        this.selector.spawnLivingEntity(this.player);
        this.location = loc;
        this.spawned = true;
    }
    public final void still() {
        this.itemDisplay.faketeleport(this.player, this.itemDisplay.getLocation());
        this.selector.teleport(this.selector.getLocation(), this.player);
        this.displayName.teleport(this.player, this.displayName.getLocation());
    }
    public final void movable() {
        Location loc = this.location.clone();
        loc.setDirection(this.player.getLocation().toVector().subtract(loc.toVector()).normalize());
        loc.setPitch(0.0f);
        Vector v = loc.getDirection().multiply(0.3f);
        this.itemDisplay.faketeleport(this.player, this.itemDisplay.getLocation().clone().add(v));
        this.displayName.faketeleport(this.player, this.displayName.getLocation().clone().add(v));
        this.selector.teleport(this.selector.getLocation().clone().add(v), this.player);
    }

    public final void teleport(Location location) {
        this.itemDisplay.teleport(this.player, this.itemDisplay.isSmall() ? location.clone().add(0.0f, 1.3f, 0.0f) : location);
        this.displayName.teleport(this.player, location.clone().add(0.0f, 0.3f, 0.0f));
        this.selector.teleport(location.clone().add(0.0f, 1.8f, 0.0f), this.player);
        this.location = location;
    }

    public final int getID() {
        return this.selector == null ? 0 : this.selector.getId();
    }

    public final Location getLocation() {
        return this.location;
    }

    public final void delete() {
        if (this.itemDisplay != null) {
            this.itemDisplay.delete();
        }

        if (this.displayName != null) {
            this.displayName.delete();
        }

        if (this.selector != null) {
            this.selector.removeLivingEntity();
        }

        this.spawned = false;
    }

    public final ReflectedArmorStand getItemDisplay() {
        return this.itemDisplay;
    }

    public final String getName() {
        return this.name;
    }

    public final Player getPlayer() {
        return this.player;
    }

    public final boolean isSpawned() {
        return this.spawned;
    }

    public final boolean isEnable() {
        return this.enable;
    }

    public final ReflectedLivingEntity getSelector() {
        return this.selector;
    }

    public final ReflectedArmorStand getDisplayName() {
        return this.displayName;
    }

    public float getYawRotation() {
        return this.yawRotation;
    }

    public final ItemStack getItem() {
        return this.item;
    }
}