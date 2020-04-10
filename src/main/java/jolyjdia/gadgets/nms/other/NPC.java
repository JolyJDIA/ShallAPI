package jolyjdia.gadgets.nms.other;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;
import jolyjdia.Main;
import jolyjdia.api.skin.Skin;
import jolyjdia.gadgets.nms.ConstructorInvoker;
import jolyjdia.gadgets.nms.FieldAccessor;
import jolyjdia.gadgets.nms.MethodInvoker;
import jolyjdia.gadgets.nms.Reflection;
import jolyjdia.gadgets.nms.player.NMSPlayer;
import jolyjdia.utils.MathUtils;
import jolyjdia.utils.UtilJava;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Array;
import java.util.UUID;

public class NPC {
    private static final Class<?> datawatcherSerializerClass = Reflection.getClass("{nms}.DataWatcherSerializer");
    private static final Class<?> datawatcherClass = Reflection.getClass("{nms}.DataWatcher");
    private static final Class<?> datawatcherObjectClass = Reflection.getClass("{nms}.DataWatcherObject");
    private static final Class<?> playerClass = Reflection.getMinecraftClass("EntityPlayer");
    private static final Class<?> entityClass = Reflection.getMinecraftClass("Entity");
    private static final Class<?> minecraftServerClass = Reflection.getClass("{nms}.MinecraftServer");
    private static final Class<?> enumInfoClass = Reflection.getClass("{nms}.PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
    private static final Class<?> interactClass = Reflection.getClass("{nms}.PlayerInteractManager");
    private static final Class<?> worldClass = Reflection.getClass("{nms}.WorldServer");
    private static final Class<?> itemStackClass = Reflection.getMinecraftClass("ItemStack");
    private static final Class<?> enumItemSlotClass = Reflection.getMinecraftClass("EnumItemSlot");
    private static final Class<?> packetTeleportClass = Reflection.getMinecraftClass("PacketPlayOutEntityTeleport");
    private static final MethodInvoker getHandleWorldMethod = Reflection.getMethod("{obc}.CraftWorld", "getHandle");
    private static final MethodInvoker getServerMethod;
    private static final ConstructorInvoker entityPlayerConstructor;
    private static final ConstructorInvoker interactManagerConstructor;
    private static final MethodInvoker setPositionMethod;
    private static final MethodInvoker getIdMethod;
    private static final MethodInvoker registerDataWatcherMethod;
    private static final ConstructorInvoker packetRotationPitchContrustuctor;
    private static final ConstructorInvoker packetRotationYawContrustuctor;
    private static final ConstructorInvoker spawnConstructor;
    private static final ConstructorInvoker infoConstructor;
    private static final Class<?> craftItemStackClass;
    private static final MethodInvoker asNMSCopyMethod;
    private static final ConstructorInvoker packetEquipConstructor;
    private static final ConstructorInvoker packetDestroyConstructor;
    private static final ConstructorInvoker datawatcherConstructor;
    private static final ConstructorInvoker datawatcherObjectConstructor;
    private static final ConstructorInvoker packetMetaConstructor;
    private static final ConstructorInvoker packetTeleportContrustuctor;
    private static final FieldAccessor datawatcherRegistry;
    private static final FieldAccessor<?> IDPacketEquipField;
    private static final FieldAccessor<?> enumItemSlotPacketEquipField;
    private static final FieldAccessor<?> itemStackPacketEquipField;
    private static final FieldAccessor<?> packetDestroyIDField;
    private Location l;
    private final Object server;
    private final Object world;
    private final Object npc;
    private final Object interact;
    private final Object packetSpawn;
    private final Object packetInfoAdd;
    private final Object packetInfoRemove;
    private final Object packetEquip;
    private Object pitch;
    private Object yaw;
    private final Object packetDestroy;
    private Object packetMeta;
    private final int id;

    static {
        getServerMethod = Reflection.getMethod(minecraftServerClass, "getServer");
        entityPlayerConstructor = Reflection.getConstructor(Reflection.getMinecraftClass("EntityPlayer"), minecraftServerClass, worldClass, GameProfile.class, interactClass);
        interactManagerConstructor = Reflection.getConstructor(interactClass, Reflection.getClass("{nms}.World"));
        setPositionMethod = Reflection.getMethod(entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
        getIdMethod = Reflection.getMethod(entityClass, "getId");
        registerDataWatcherMethod = Reflection.getMethod(datawatcherClass, "register", datawatcherObjectClass, Object.class);
        Class<?> packetEquipClass = Reflection.getMinecraftClass("PacketPlayOutEntityEquipment");
        Class<?> pitch = Reflection.getMinecraftClass("PacketPlayOutEntity$PacketPlayOutEntityLook");
        Class<?> packetMetaClass = Reflection.getMinecraftClass("PacketPlayOutEntityMetadata");
        packetRotationPitchContrustuctor = Reflection.getConstructor(pitch, Integer.TYPE, Byte.TYPE, Byte.TYPE, Boolean.TYPE);
        Class<?> yaw = Reflection.getMinecraftClass("PacketPlayOutEntityHeadRotation");
        packetRotationYawContrustuctor = Reflection.getConstructor(yaw, entityClass, Byte.TYPE);
        spawnConstructor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutNamedEntitySpawn"), Reflection.getMinecraftClass("EntityHuman"));
        infoConstructor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutPlayerInfo"), enumInfoClass, Array.newInstance(playerClass, 0).getClass());
        craftItemStackClass = Reflection.getClass("{obc}.inventory.CraftItemStack");
        asNMSCopyMethod = Reflection.getMethod(craftItemStackClass, "asNMSCopy", ItemStack.class);
        packetEquipConstructor = Reflection.getConstructor(packetEquipClass);
        Class<?> packetDestroyClass = Reflection.getMinecraftClass("PacketPlayOutEntityDestroy");
        packetDestroyConstructor = Reflection.getConstructor(packetDestroyClass);
        datawatcherConstructor = Reflection.getConstructor(datawatcherClass, entityClass);
        datawatcherObjectConstructor = Reflection.getConstructor(datawatcherObjectClass, Integer.TYPE, datawatcherSerializerClass);
        packetMetaConstructor = Reflection.getConstructor(packetMetaClass, Integer.TYPE, datawatcherClass, Boolean.TYPE);
        packetTeleportContrustuctor = Reflection.getConstructor(packetTeleportClass, entityClass);
        datawatcherRegistry = Reflection.getField(Reflection.getMinecraftClass("DataWatcherRegistry"), Object.class, 1);
        IDPacketEquipField = Reflection.getField(packetEquipClass, Integer.TYPE, 0);
        enumItemSlotPacketEquipField = Reflection.getField((Class) packetEquipClass, enumItemSlotClass, 0);
        itemStackPacketEquipField = Reflection.getField((Class) packetEquipClass, itemStackClass, 0);
        packetDestroyIDField = Reflection.getField(packetDestroyClass, Object.class, 0);
    }

    public NPC(Location var1, String var2, Skin var3) {
        this.l = var1;
        this.server = getServerMethod.invoke(minecraftServerClass);
        this.world = getHandleWorldMethod.invoke(var1.getWorld());
        this.interact = interactManagerConstructor.invoke(this.world);
        GameProfile var4 = (new ProfileLoader(var2)).loadProfile(var3);
        this.npc = entityPlayerConstructor.invoke(this.server, this.world, var4, this.interact);
        setPositionMethod.invoke(this.npc, var1.getX(), var1.getY(), var1.getZ(), var1.getYaw(), var1.getPitch());
        this.id = (Integer)getIdMethod.invoke(this.npc);
        this.packetSpawn = spawnConstructor.invoke(this.npc);
        Object var5 = UtilJava.getArray(playerClass, this.npc);
        this.packetInfoAdd = infoConstructor.invoke(enumInfoClass.getEnumConstants()[0], var5);
        this.packetInfoRemove = infoConstructor.invoke(enumInfoClass.getEnumConstants()[4], var5);
        this.pitch = packetRotationPitchContrustuctor.invoke(this.id, MathUtils.toPackedByte(var1.getYaw()), MathUtils.toPackedByte(var1.getPitch()), false);
        this.yaw = packetRotationYawContrustuctor.invoke(this.npc, MathUtils.toPackedByte(var1.getYaw()));
        this.packetEquip = packetEquipConstructor.invoke();
        IDPacketEquipField.set(this.packetEquip, this.id);
        this.packetDestroy = packetDestroyConstructor.invoke();
        packetDestroyIDField.set(this.packetDestroy, new int[]{this.id});
    }

    public NPC(Location var1, Player var2) {
        this.l = var1;
        GameProfile var3 = Reflection.getField(Reflection.getMinecraftClass("EntityHuman"), GameProfile.class, 0).get(NMSPlayer.getHandle(var2));
        GameProfile profile = new GameProfile(UUID.randomUUID(), var2.getName());
        Reflection.getField(GameProfile.class, PropertyMap.class, 0).set(profile, Reflection.getField(GameProfile.class, PropertyMap.class, 0).get(var3));
        this.server = getServerMethod.invoke(minecraftServerClass);
        this.world = getHandleWorldMethod.invoke(var1.getWorld());
        this.interact = interactManagerConstructor.invoke(this.world);
        this.npc = entityPlayerConstructor.invoke(this.server, this.world, profile, this.interact);
        setPositionMethod.invoke(this.npc, var1.getX(), var1.getY(), var1.getZ(), var1.getYaw(), var1.getPitch());
        this.id = (Integer)getIdMethod.invoke(this.npc);
        this.packetSpawn = spawnConstructor.invoke(this.npc);
        Object var4 = UtilJava.getArray(playerClass, this.npc);
        this.packetInfoAdd = infoConstructor.invoke(enumInfoClass.getEnumConstants()[0], var4);
        this.packetInfoRemove = infoConstructor.invoke(enumInfoClass.getEnumConstants()[4], var4);
        this.pitch = packetRotationPitchContrustuctor.invoke(this.id, MathUtils.toPackedByte(var1.getYaw()), MathUtils.toPackedByte(var1.getPitch()), false);
        this.yaw = packetRotationYawContrustuctor.invoke(this.npc, MathUtils.toPackedByte(var1.getYaw()));
        this.packetEquip = packetEquipConstructor.invoke();
        IDPacketEquipField.set(this.packetEquip, this.id);
        this.packetDestroy = packetDestroyConstructor.invoke();
        packetDestroyIDField.set(this.packetDestroy, new int[]{this.id});
        Object var5 = datawatcherConstructor.invoke(this.npc);
        Object datawatcherObject = datawatcherObjectConstructor.invoke(12, datawatcherRegistry.get(Reflection.getMinecraftClass("DataWatcherRegistry")));
        registerDataWatcherMethod.invoke(var5, datawatcherObject, -1);
        this.packetMeta = packetMetaConstructor.invoke(this.id, var5, true);
    }

    public final void teleport(Location var1) {
        setPositionMethod.invoke(this.npc, var1.getX(), var1.getY(), var1.getZ(), 0.0F, 0.0F);
        Object var2 = packetTeleportContrustuctor.invoke(this.npc);
        NMSPlayer.sendPacket(var1.getWorld(), var2);
        this.l = var1;
    }

    public final void setDirection(Location var1) {
        Location var2 = var1.clone();
        Vector var3 = var1.toVector().subtract(this.l.toVector());
        var2.setDirection(var3);
        this.pitch = packetRotationPitchContrustuctor.invoke(this.id, MathUtils.toPackedByte(var2.getYaw()), MathUtils.toPackedByte(var2.getPitch()), false);
        this.yaw = packetRotationYawContrustuctor.invoke(this.npc, MathUtils.toPackedByte(var2.getYaw()));
        NMSPlayer.sendPacket(var1.getWorld(), this.pitch, this.yaw);
    }

    public final void setDirection(double var1, double var2) {
        this.pitch = packetRotationPitchContrustuctor.invoke(this.id, MathUtils.toPackedByte(var1), MathUtils.toPackedByte(var2), false);
        this.yaw = packetRotationYawContrustuctor.invoke(this.npc, MathUtils.toPackedByte(var1));
        NMSPlayer.sendPacket(this.l.getWorld(), this.pitch, this.yaw);
    }

    public final void equip(int var1, ItemStack var2) {
        enumItemSlotPacketEquipField.set(this.packetEquip, enumItemSlotClass.getEnumConstants()[var1]);
        itemStackPacketEquipField.set(this.packetEquip, asNMSCopyMethod.invoke(craftItemStackClass, var2));
        NMSPlayer.sendPacket(this.l.getWorld(), this.packetEquip);
    }

    public final void spawnNPC() {
        if (this.packetMeta == null) {
            NMSPlayer.sendPacketNearby(this.l, this.packetInfoAdd, this.packetSpawn);
        } else {
            NMSPlayer.sendPacketNearby(this.l, this.packetInfoAdd, this.packetSpawn, this.packetMeta);
        }

        (new BukkitRunnable() {
            @Override
            public void run() {
                NMSPlayer.sendPacket(NPC.this.l.getWorld(), NPC.this.packetInfoRemove, NPC.this.pitch, NPC.this.yaw);
            }
        }).runTaskLater(Main.getInstance(), 5L);
    }

    public final Location getLocation() {
        return this.l;
    }

    public final void remove() {
        NMSPlayer.sendPacket(this.l.getWorld(), this.packetDestroy);
    }
}
