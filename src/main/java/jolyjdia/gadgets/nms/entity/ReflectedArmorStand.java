package jolyjdia.gadgets.nms.entity;

import jolyjdia.gadgets.nms.ConstructorInvoker;
import jolyjdia.gadgets.nms.FieldAccessor;
import jolyjdia.gadgets.nms.MethodInvoker;
import jolyjdia.gadgets.nms.Reflection;
import jolyjdia.gadgets.nms.player.NMSPlayer;
import jolyjdia.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;

public class ReflectedArmorStand {
    private static final Class<?> packetEquipClass = Reflection.getClass("{nms}.PacketPlayOutEntityEquipment");
    private static final Class<?> entityArmorStandClass = Reflection.getClass("{nms}.EntityArmorStand");
    private static final Class<?> entityClass = Reflection.getClass("{nms}.Entity");
    private static final Class<?> vector3fClass = Reflection.getMinecraftClass("Vector3f");
    private static final MethodInvoker setPositionMethod = Reflection.getMethod(entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
    private static final MethodInvoker setCustomeNameMethod = Reflection.getMethod(entityArmorStandClass, "setCustomName", String.class);
    private static final MethodInvoker setSmallMethod = Reflection.getMethod(entityArmorStandClass, "setSmall", Boolean.TYPE);
    private static final MethodInvoker setHeadPoseMethod = Reflection.getMethod(entityArmorStandClass, "setHeadPose", vector3fClass);
    private static final MethodInvoker setRightArmPosePoseMethod = Reflection.getMethod(entityArmorStandClass, "setRightArmPose", vector3fClass);
    private static final ConstructorInvoker packetmetaConstructor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutEntityMetadata"), Integer.TYPE, Reflection.getMinecraftClass("DataWatcher"), Boolean.TYPE);
    private static final ConstructorInvoker armorStandConstructor = Reflection.getConstructor(entityArmorStandClass, Reflection.getClass("{nms}.World"));
    private static final ConstructorInvoker packetEquipConstructor = Reflection.getConstructor(packetEquipClass);
    private static final ConstructorInvoker packetSpawnConstructor = Reflection.getConstructor(Reflection.getClass("{nms}.PacketPlayOutSpawnEntityLiving"), Reflection.getClass("{nms}.EntityLiving"));
    private static final ConstructorInvoker packetRotationContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutEntity$PacketPlayOutEntityLook"), Integer.TYPE, Byte.TYPE, Byte.TYPE, Boolean.TYPE);
    private static final ConstructorInvoker packetTeleportContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutEntityTeleport"), entityClass);
    private static final ConstructorInvoker vector3fConstructor = Reflection.getConstructor(vector3fClass, Float.TYPE, Float.TYPE, Float.TYPE);
    private static final FieldAccessor<?> IDPacketEquipField = Reflection.getField(packetEquipClass, Integer.TYPE, 0);
    private static final FieldAccessor<?> enumItemSlotPacketEquipField = Reflection.getField(packetEquipClass, Reflection.getMinecraftClass("EnumItemSlot"), 0);
    private static final FieldAccessor<?> itemStackPacketEquipField = Reflection.getField((Class)packetEquipClass, Reflection.getMinecraftClass("ItemStack"), 0);
    private static final FieldAccessor<?> datawatcherField = Reflection.getField(entityClass, "datawatcher", Reflection.getMinecraftClass("DataWatcher"));
    private static final MethodInvoker setMarkerMethod = Reflection.getMethod(entityArmorStandClass, "setMarker", Boolean.TYPE);
    private final Object packetSpawn;
    private final List<Object> packetEquip = new ArrayList<>();
    private Object packetRotation;
    private List<Object> packets;
    private final int ID;
    private Location location;
    private String displayName = "";
    private boolean small;
    private float yaw;
    private float pitch;
    private final Object armorStand;

    public ReflectedArmorStand(Location var1) {
        this.location = var1;
        this.armorStand = armorStandConstructor.invoke(Reflection.getMethod("{obc}.CraftWorld", "getHandle").invoke(var1.getWorld()));
        setPositionMethod.invoke(this.armorStand, var1.getX(), var1.getY(), var1.getZ(), var1.getYaw(), var1.getPitch());
        this.packetSpawn = packetSpawnConstructor.invoke(this.armorStand);
        this.ID = (Integer)Reflection.getMethod(entityClass, "getId").invoke(this.armorStand);
    }

    public final void updateName(String var1, Player var2) {
        setCustomeNameMethod.invoke(this.armorStand, var1);
        NMSPlayer.sendPacket(var2, packetmetaConstructor.invoke(this.ID, datawatcherField.get(this.armorStand), true));

    }

    public final void setHeadPose(EulerAngle var1) {
        EulerAngle var2 = toNMS(var1);
        setHeadPoseMethod.invoke(this.armorStand, vector3fConstructor.invoke(var2.getX(), var2.getY(), var2.getZ()));
    }

    public final void setRightArmPose(EulerAngle var1) {
        EulerAngle var2 = toNMS(var1);
        setRightArmPosePoseMethod.invoke(this.armorStand, vector3fConstructor.invoke(var2.getX(), var2.getY(), var2.getZ()));
    }

    public final void setRotation(float var1, float var2) {
        this.packetRotation = packetRotationContrustuctor.invoke(this.ID, MathUtils.toPackedByte(var1), MathUtils.toPackedByte(var2), false);
        this.yaw = var1;
        this.pitch = var2;
        NMSPlayer.sendPacket(this.location.getWorld(), this.packetRotation);
    }
    public final void setMarker() {
        setMarkerMethod.invoke(this.armorStand, true);
    }

    public final void updateMetadata() {
        NMSPlayer.sendPacket(this.location.getWorld(), packetmetaConstructor.invoke(this.ID, datawatcherField.get(this.armorStand), true));
    }

    public final void setEquipment(int i, ItemStack stack) {
        Object o = packetEquipConstructor.invoke();
        IDPacketEquipField.set(o, this.ID);
        enumItemSlotPacketEquipField.set(o, Reflection.getMinecraftClass("EnumItemSlot").getEnumConstants()[i]);
        itemStackPacketEquipField.set(o, Reflection.getMethod(Reflection.getClass("{obc}.inventory.CraftItemStack"), "asNMSCopy", ItemStack.class).invoke(Reflection.getClass("{obc}.inventory.CraftItemStack"), stack));
        this.packetEquip.add(o);
    }
    public final void setVisible(boolean var1) {
        Reflection.getMethod(entityArmorStandClass, "setInvisible", Boolean.TYPE).invoke(this.armorStand, !var1);
    }

    public final boolean isSmall() {
        return this.small;
    }

    public final void setSmall(boolean var1) {
        setSmallMethod.invoke(this.armorStand, var1);
        this.small = var1;
    }

    public final void teleport(Player var1, Location var2) {
        setPositionMethod.invoke(this.armorStand, var2.getX(), var2.getY(), var2.getZ(), this.yaw, this.pitch);
        NMSPlayer.sendPacket(var1, packetTeleportContrustuctor.invoke(this.armorStand));
        this.location = var2;
    }

    public final void faketeleport(Player var1, Location var2) {
        setPositionMethod.invoke(this.armorStand, var2.getX(), var2.getY(), var2.getZ(), this.yaw, this.pitch);
        NMSPlayer.sendPacket(var1, packetTeleportContrustuctor.invoke(this.armorStand));
    }

    public final void teleport(Location var1) {
        setPositionMethod.invoke(this.armorStand, var1.getX(), var1.getY(), var1.getZ(), this.yaw, this.pitch);
        NMSPlayer.sendPacket(this.location.getWorld(), packetTeleportContrustuctor.invoke(this.armorStand));
        this.location = var1;
    }

    public final void delete() {
        NMSPlayer.destroyEntity(this.location.getWorld(), this.ID);
        if (this.packets != null) {
            NMSPlayer.getPacketsToSend().removeAll(this.packets);
        }

    }

    public final void spawnArmorStand() {
        NMSPlayer.sendPacketNearby(this.location, this.packetSpawn, this.packetRotation);
        for(Object o : packetEquip) {
            NMSPlayer.sendPacketNearby(this.location, o);
        }
    }

    public final void spawnConstantArmorStand() {
        this.packets = NMSPlayer.sendConstantPacketObj(this.location.getWorld(), this.packetSpawn, this.packetRotation);
        for(Object o : packetEquip) {
            NMSPlayer.sendConstantPacket(this.location.getWorld(), o);
        }
        this.packets.addAll(this.packetEquip);
    }

    public final void spawnArmorStand(Player p) {
        NMSPlayer.sendPacket(p, this.packetSpawn, this.packetRotation);
        for(Object o : packetEquip) {
            NMSPlayer.sendPacket(this.location.getWorld(), o);
        }
    }

    public final void spawnArmorStandExlude(Player p) {
        NMSPlayer.sendPacketExclude(Bukkit.getOnlinePlayers(), p, this.packetSpawn, this.packetRotation);
        for(Object o : packetEquip) {
            NMSPlayer.sendPacketExclude(Bukkit.getOnlinePlayers(), p, o);
        }
    }

    private static EulerAngle toNMS(EulerAngle ea) {
        return new EulerAngle((Math.toDegrees(ea.getX())), (Math.toDegrees(ea.getY())), (Math.toDegrees(ea.getZ())));
    }

    public final Location getLocation() {
        return this.location;
    }

    public final void setLocation(Location var1) {
        this.location = var1;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final void setDisplayName(String var1) {
        setCustomeNameMethod.invoke(this.armorStand, var1);
        Reflection.getMethod(entityArmorStandClass, "setCustomNameVisible", Boolean.TYPE).invoke(this.armorStand, true);
        this.displayName = var1;
    }

    public final float getYaw() {
        return this.yaw;
    }
}
