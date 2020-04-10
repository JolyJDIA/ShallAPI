package jolyjdia.gadgets.nms.other;

import jolyjdia.gadgets.nms.ConstructorInvoker;
import jolyjdia.gadgets.nms.FieldAccessor;
import jolyjdia.gadgets.nms.MethodInvoker;
import jolyjdia.gadgets.nms.Reflection;
import jolyjdia.gadgets.nms.player.NMSPlayer;
import jolyjdia.utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

public class NMS {
    private static final Class<?> entityClass = Reflection.getClass("{nms}.Entity");
    private static final Class<?> minecraftServerClass = Reflection.getClass("{nms}.MinecraftServer");
    private static final MethodInvoker getHandleWorldMethod = Reflection.getMethod("{obc}.CraftWorld", "getHandle");
    private static final FieldAccessor noClipField;
    private static final MethodInvoker getHandleMethod;
    private static final MethodInvoker getServerMethod;
    private static final MethodInvoker setInvisibleMethod;
    private static @Nullable MethodInvoker setSilentMethod;
    private static final MethodInvoker setPositionMethod;
    private static final FieldAccessor worldField;
    private static final MethodInvoker broadcastentityeffectMethod;
    private static final ConstructorInvoker packetRotationPitchContrustuctor;
    private static final ConstructorInvoker packetRotationYawContrustuctor;
    private static final ConstructorInvoker packetSpawnContrustuctor;
    private static final ConstructorInvoker packetStatusContrustuctor;
    private static final ConstructorInvoker dragonContrustuctor;
    private static final MethodInvoker getIdMethod;
    private static final FieldAccessor recentTps;
    private static final FieldAccessor lastX;
    private static final FieldAccessor lastY;
    private static final FieldAccessor lastZ;
    private static final ConstructorInvoker blockpositionContrustuctor;
    private static final ConstructorInvoker blockactionContrustuctor;
    private static final FieldAccessor motXField;
    private static final FieldAccessor motZField;
    private static final FieldAccessor motYField;
    private static final FieldAccessor idField;

    static {
        noClipField = Reflection.getField(Reflection.getMinecraftClass("Entity"), "noclip", Boolean.TYPE);
        getHandleMethod = Reflection.getMethod("{obc}.entity.CraftEntity", "getHandle");
        getServerMethod = Reflection.getMethod(minecraftServerClass, "getServer");
        setInvisibleMethod = Reflection.getMethod(entityClass, "setInvisible", Boolean.TYPE);
        setPositionMethod = Reflection.getMethod(entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
        worldField = Reflection.getField((Class)entityClass, Reflection.getMinecraftClass("World"), 0);
        broadcastentityeffectMethod = Reflection.getMethod(Reflection.getMinecraftClass("World"), "broadcastEntityEffect", entityClass, Byte.TYPE);
        Class<?> packetRotationPitchClass = Reflection.getMinecraftClass("PacketPlayOutEntity$PacketPlayOutEntityLook");
        packetRotationPitchContrustuctor = Reflection.getConstructor(packetRotationPitchClass, Integer.TYPE, Byte.TYPE, Byte.TYPE, Boolean.TYPE);
        Class<?> packetRotationYawClass = Reflection.getMinecraftClass("PacketPlayOutEntityHeadRotation");
        packetRotationYawContrustuctor = Reflection.getConstructor(packetRotationYawClass, entityClass, Byte.TYPE);
        packetSpawnContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutSpawnEntityLiving"), Reflection.getMinecraftClass("EntityLiving"));
        packetStatusContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutEntityStatus"), entityClass, Byte.TYPE);
        dragonContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("EntityEnderDragon"), Reflection.getMinecraftClass("World"));
        getIdMethod = Reflection.getMethod(entityClass, "getId");
        recentTps = Reflection.getField(minecraftServerClass, "recentTps", double[].class);
        lastX = Reflection.getField(entityClass, "lastX", Double.TYPE);
        lastY = Reflection.getField(entityClass, "lastY", Double.TYPE);
        lastZ = Reflection.getField(entityClass, "lastZ", Double.TYPE);
        blockpositionContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("BlockPosition"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
        blockactionContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutBlockAction"), Reflection.getMinecraftClass("BlockPosition"), Reflection.getMinecraftClass("Block"), Integer.TYPE, Integer.TYPE);
        motXField = Reflection.getField(entityClass, "motX", Double.TYPE);
        motZField = Reflection.getField(entityClass, "motZ", Double.TYPE);
        motYField = Reflection.getField(entityClass, "motY", Double.TYPE);
        idField = Reflection.getField(entityClass, "id", Integer.TYPE);

        try {
            setSilentMethod = Reflection.getMethod(Reflection.getMinecraftClass("Entity"), "setSilent", Boolean.TYPE);
        } catch (IllegalStateException var0) {
            setSilentMethod = null;
        }

    }

    public static void setNoClip(Entity var0, boolean var1) {
        noClipField.set(getHandleMethod.invoke(var0), var1);
    }

    public static void broadcastEntityEffect(Entity var0, byte var1) {
        Object var2 = worldField.get(getHandleMethod.invoke(var0));
        broadcastentityeffectMethod.invoke(var2, getHandleMethod.invoke(var0), var1);
    }

    public static void ironGolemAttack(IronGolem var0) {
        Object var1 = worldField.get(getHandleMethod.invoke(var0));
        broadcastentityeffectMethod.invoke(var1, getHandleMethod.invoke(var0), 4);
    }

    public static void setPitchHeadRotation(Entity var0, float var1) {
        Object var2 = packetRotationPitchContrustuctor.invoke(getIdMethod.invoke(getHandleMethod.invoke(var0)), MathUtils.toPackedByte(var0.getLocation().getYaw()), MathUtils.toPackedByte(var1), false);
        NMSPlayer.sendPacketNearby(var0.getLocation(), var2);
    }

    public static void setYawRotation(Entity var0, float var1) {
        Object var2 = packetRotationYawContrustuctor.invoke(getHandleMethod.invoke(var0), MathUtils.toPackedByte(var1));
        NMSPlayer.sendPacketNearby(var0.getLocation(), var2);
    }

    public static Vector getLastLocation(Entity var0) {
        Object var1 = getHandleMethod.invoke(var0);
        return new Vector((Double)lastX.get(var1), (Double)lastY.get(var1), (Double)lastZ.get(var1));
    }

    public static void setChestOpen(Block var0, boolean var1) {
        NMSPlayer.sendPacketNearby(var0.getLocation(), blockactionContrustuctor.invoke(blockpositionContrustuctor.invoke(var0.getX(), var0.getY(), var0.getZ()), Reflection.getField(Reflection.getMinecraftClass("Blocks"), "CHEST", Reflection.getMinecraftClass("Block")).get(Reflection.getMinecraftClass("Blocks")), 1, var1 ? 1 : 0));
    }

    public static void setMotion(Entity var0, float var1, float var3) {
        Object var5 = getHandleMethod.invoke(var0);
        motXField.set(var5, var1);
        motZField.set(var5, var3);
    }

    public static Vector getMotion(Entity var0) {
        Object var1 = getHandleMethod.invoke(var0);
        return new Vector((Double)motXField.get(var1), (Double)motYField.get(var1), (Double)motZField.get(var1));
    }

    public static int spawnDragonEffect(Location var0) {
        Object var1 = dragonContrustuctor.invoke(getHandleWorldMethod.invoke(var0.getWorld()));
        setPositionMethod.invoke(var1, var0.getX(), var0.getY(), var0.getZ(), var0.getYaw(), var0.getPitch());
        Object var2 = packetSpawnContrustuctor.invoke(var1);
        Object var3 = packetStatusContrustuctor.invoke(var1, 3);
        NMSPlayer.sendPacketNearby(var0, var2, var3);
        return (Integer)getIdMethod.invoke(var1);
    }

    public static void setInvisible(Entity var0, boolean var1) {
        setInvisibleMethod.invoke(getHandleMethod.invoke(var0), var1);
    }

    public static int getID(Entity var0) {
        return (Integer)getIdMethod.invoke(getHandleMethod.invoke(var0));
    }

    public static void setId(Entity var0, Entity var1) {
        idField.set(getHandleMethod.invoke(var0), getID(var1));
    }

    public static double getTPS() {
        Object o = getServerMethod.invoke(minecraftServerClass);
        double[] floats = (double[])recentTps.get(o);
        return floats[0];
    }

    public static void setSilent(Entity var0) {
        if (setSilentMethod != null) {
            setSilentMethod.invoke(getHandleMethod.invoke(var0), true);
        }
    }
}
