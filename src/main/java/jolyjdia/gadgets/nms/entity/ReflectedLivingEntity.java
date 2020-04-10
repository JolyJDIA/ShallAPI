package jolyjdia.gadgets.nms.entity;

import jolyjdia.gadgets.nms.ConstructorInvoker;
import jolyjdia.gadgets.nms.MethodInvoker;
import jolyjdia.gadgets.nms.Reflection;
import jolyjdia.gadgets.nms.player.NMSPlayer;
import jolyjdia.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class ReflectedLivingEntity {
    private static final Class<?> packetRotationPitchClass = Reflection.getMinecraftClass("PacketPlayOutEntity$PacketPlayOutEntityLook");
    private static final Class<?> packetSpawnEntityLivingClass = Reflection.getClass("{nms}.PacketPlayOutSpawnEntityLiving");
    private static final Class<?> packetRotationYawClass = Reflection.getMinecraftClass("PacketPlayOutEntityHeadRotation");
    private static final Class<?> nmsWorldClass = Reflection.getClass("{nms}.World");
    private static final Class<?> entityClass = Reflection.getClass("{nms}.Entity");
    private static final Class<?> entityLivingClass = Reflection.getClass("{nms}.EntityLiving");
    private static final Class<?> packetTeleportClass = Reflection.getMinecraftClass("PacketPlayOutEntityTeleport");
    private static final ConstructorInvoker packetSpawnConstructor;
    private static final ConstructorInvoker packetRotationPitchContrustuctor;
    private static final ConstructorInvoker packetRotationYawContrustuctor;
    private static final ConstructorInvoker packetTeleportContrustuctor;
    private static final MethodInvoker getHandleWorldMethod;
    private static final MethodInvoker setPositionMethod;
    private static final MethodInvoker getIdMethod;
    private static final MethodInvoker getBukkitEntityMethod;
    private static final MethodInvoker setInvisibleMethod;
    private final Class<?> entityTypeClass;
    private List<Object> packets;
    private Location location;
    private final Object livingEntity;
    private final Entity entityBukkit;
    private final EntityType entityType;
    private boolean isSpawned;
    private final int ID;
    private float yaw;
    private float pitch;
    private final Object packetSpawn;
    private Object packetYawRotation;
    private Object packetPitchRotation;

    static {
        packetSpawnConstructor = Reflection.getConstructor(packetSpawnEntityLivingClass, entityLivingClass);
        packetRotationPitchContrustuctor = Reflection.getConstructor(packetRotationPitchClass, Integer.TYPE, Byte.TYPE, Byte.TYPE, Boolean.TYPE);
        packetRotationYawContrustuctor = Reflection.getConstructor(packetRotationYawClass, entityClass, Byte.TYPE);
        packetTeleportContrustuctor = Reflection.getConstructor(packetTeleportClass, entityClass);
        getHandleWorldMethod = Reflection.getMethod("{obc}.CraftWorld", "getHandle");
        setPositionMethod = Reflection.getMethod(entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
        getIdMethod = Reflection.getMethod(entityClass, "getId");
        getBukkitEntityMethod = Reflection.getMethod(entityClass, "getBukkitEntity");
        setInvisibleMethod = Reflection.getMethod(entityClass, "setInvisible", Boolean.TYPE);
    }

    public ReflectedLivingEntity(Location var1, EntityType var2) {
        this.location = var1;
        this.entityType = var2;
        this.entityTypeClass = Reflection.getMinecraftClass("Entity" + var2.getEntityClass().getSimpleName());
        ConstructorInvoker var3 = Reflection.getConstructor(Reflection.getMinecraftClass("Entity" + var2.getEntityClass().getSimpleName()), nmsWorldClass);
        this.livingEntity = var3.invoke(getHandleWorldMethod.invoke(var1.getWorld()));
        setPositionMethod.invoke(this.livingEntity, var1.getX(), var1.getY(), var1.getZ(), var1.getYaw(), var1.getPitch());
        this.ID = (Integer)getIdMethod.invoke(this.livingEntity);
        this.packetSpawn = packetSpawnConstructor.invoke(this.livingEntity);
        this.entityBukkit = (Entity)getBukkitEntityMethod.invoke(this.livingEntity);
    }

    public final void setSize(int var1) {
        if (this.entityType == EntityType.SLIME || this.entityType == EntityType.MAGMA_CUBE) {
            String var2 = this.getPackageName();
            MethodInvoker var3;
            if (!var2.equalsIgnoreCase("v1_11_R1") && !var2.equalsIgnoreCase("v1_11_R2") && !var2.equalsIgnoreCase("v1_12_R1") && !var2.equalsIgnoreCase("v1_12_R2")) {
                var3 = Reflection.getMethod(this.entityTypeClass, "setSize", Integer.TYPE);
                var3.invoke(this.livingEntity, var1);
            } else {
                var3 = Reflection.getMethod(this.entityTypeClass, "setSize", Integer.TYPE, Boolean.TYPE);
                var3.invoke(this.livingEntity, var1, false);
            }
        }

    }

    public final void setVisible(boolean var1) {
        setInvisibleMethod.invoke(this.livingEntity, !var1);
    }

    public final void teleport(Location var1, float var2, float var3) {
        setPositionMethod.invoke(this.livingEntity, var1.getX(), var1.getY(), var1.getZ(), var2, var3);
        Object var4 = packetTeleportContrustuctor.invoke(this.livingEntity);
        NMSPlayer.sendPacketNearby(var1, var4);
        this.location = var1;
    }

    public final void teleport(Location var1, Player var2) {
        setPositionMethod.invoke(this.livingEntity, var1.getX(), var1.getY(), var1.getZ(), this.yaw, this.pitch);
        Object var3 = packetTeleportContrustuctor.invoke(this.livingEntity);
        NMSPlayer.sendPacket(var2, var3);
    }

    public final void setYawRotation(float var1) {
        this.packetYawRotation = packetRotationYawContrustuctor.invoke(this.livingEntity, MathUtils.toPackedByte(var1));
        if (this.isSpawned) {
            NMSPlayer.sendPacketNearby(this.location, this.packetYawRotation);
        }

        this.yaw = var1;
    }

    public final void setPitchRotation(float var1) {
        this.packetPitchRotation = packetRotationPitchContrustuctor.invoke(this.ID, MathUtils.toPackedByte(this.yaw), MathUtils.toPackedByte(var1), false);
        if (this.isSpawned) {
            NMSPlayer.sendPacketNearby(this.location, this.packetPitchRotation);
        }

        this.pitch = var1;
    }

    public final void spawnLivingEntity() {
        this.packets = NMSPlayer.sendConstantPacketObj(this.location.getWorld(), this.packetSpawn, this.packetYawRotation, this.packetPitchRotation);
        this.isSpawned = true;
    }

    public final void spawnLivingEntity(Player var1) {
        NMSPlayer.sendPacket(var1, this.packetSpawn, this.packetYawRotation, this.packetPitchRotation);
        this.isSpawned = true;
    }

    private final String getPackageName() {
        String var1 = Bukkit.getServer().getClass().getPackage().getName();
        return var1.substring(var1.lastIndexOf(46) + 1);
    }

    public final float getYaw() {
        return this.yaw;
    }

    public final float getPitch() {
        return this.pitch;
    }

    public final void removeLivingEntity() {
        NMSPlayer.destroyEntity(this.location.getWorld(), this.ID);
        if (this.packets != null) {
            NMSPlayer.getPacketsToSend().removeAll(this.packets);
        }

    }

    public final Entity getBukkitEntity() {
        return this.entityBukkit;
    }

    public final Location getLocation() {
        return this.location;
    }

    public final int getId() {
        return this.ID;
    }
}
