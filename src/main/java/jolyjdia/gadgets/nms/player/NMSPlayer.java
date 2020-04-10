package jolyjdia.gadgets.nms.player;

import jolyjdia.gadgets.nms.ConstructorInvoker;
import jolyjdia.gadgets.nms.FieldAccessor;
import jolyjdia.gadgets.nms.MethodInvoker;
import jolyjdia.gadgets.nms.Reflection;
import jolyjdia.utils.UtilLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public final class NMSPlayer {
    private static final List<Object> packetsToSend = new ArrayList<>();
    private static final Map<UUID, List<Object>> packetsToSendPlayer = new HashMap<>();
    private static final Map<UUID, List<Object>> packetsToSendToOthers = new HashMap<>();
    private static final Class<?> packetSpawnNamedClass = Reflection.getClass("{nms}.PacketPlayOutNamedEntitySpawn");
    private static final Class<?> entityHumanClass = Reflection.getClass("{nms}.EntityHuman");
    private static final Class<?> packetDestroyClass = Reflection.getMinecraftClass("PacketPlayOutEntityDestroy");
    private static final Class<?> packetEquipClass = Reflection.getMinecraftClass("PacketPlayOutEntityEquipment");
    private static final Class<?> enumItemSlotClass = Reflection.getMinecraftClass("EnumItemSlot");
    private static final Class<?> craftItemStackClass = Reflection.getClass("{obc}.inventory.CraftItemStack");
    private static final Class<?> itemStackClass = Reflection.getMinecraftClass("ItemStack");
    private static final FieldAccessor<?> playerConnectionField = Reflection.getField("{nms}.EntityPlayer", "playerConnection", Object.class);
    private static final FieldAccessor<?> IDPacketEquipField;
    private static final FieldAccessor<?> packetDestroyIDField;
    private static final FieldAccessor<?> enumItemSlotPacketEquipField;
    private static final FieldAccessor<?> itemStackPacketEquipField;
    private static final MethodInvoker getHandlePlayerMethod;
    private static final MethodInvoker getHandleMethod;
    private static final MethodInvoker getIdMethod;
    private static final MethodInvoker sendPacket;
    private static final MethodInvoker asNMSCopyMethod;
    private static final ConstructorInvoker packetSpawnHumanConstructor;
    private static final ConstructorInvoker packetEquipConstructor;
    private static final ConstructorInvoker packetDestroyConstructor;

    static {
        IDPacketEquipField = Reflection.getField(packetEquipClass, Integer.TYPE, 0);
        packetDestroyIDField = Reflection.getField(packetDestroyClass, Object.class, 0);
        enumItemSlotPacketEquipField = Reflection.getField((Class)packetEquipClass, enumItemSlotClass, 0);
        itemStackPacketEquipField = Reflection.getField((Class)packetEquipClass, itemStackClass, 0);
        getHandlePlayerMethod = Reflection.getMethod("{obc}.entity.CraftPlayer", "getHandle");
        getHandleMethod = Reflection.getMethod("{obc}.entity.CraftEntity", "getHandle");
        getIdMethod = Reflection.getMethod("{nms}.Entity", "getId");
        sendPacket = Reflection.getMethod("{nms}.PlayerConnection", "sendPacket", Reflection.getMinecraftClass("Packet"));
        asNMSCopyMethod = Reflection.getMethod(craftItemStackClass, "asNMSCopy", ItemStack.class);
        packetSpawnHumanConstructor = Reflection.getConstructor(packetSpawnNamedClass, entityHumanClass);
        packetEquipConstructor = Reflection.getConstructor(packetEquipClass);
        packetDestroyConstructor = Reflection.getConstructor(packetDestroyClass);
    }

    private NMSPlayer() {
    }

    public static void sendPacket(Player p, Object... obj) {
        for(int i = 0; i < obj.length; ++i) {
            Object o = obj[i];
            if (o != null) {
                sendPacket.invoke(playerConnectionField.get(getHandleMethod.invoke(p)), o);
            }
        }

    }

    public static void sendPacket(List<Player> list, Object... obj) {
        for(int i = 0; i < obj.length; ++i) {
            Object o = obj[i];
            if (o != null) {
                list.forEach((var1x) -> sendPacket.invoke(playerConnectionField.get(getHandleMethod.invoke(var1x)), o));
            }
        }
    }
    public static void sendPacket(World w, Object... obj) {
        for(int i = 0; i < obj.length; ++i) {
            Object o = obj[i];
            if (o != null) {
                Bukkit.getOnlinePlayers().stream().filter(var1x -> var1x.getWorld().equals(w)).forEach((var1x) -> sendPacket.invoke(playerConnectionField.get(getHandleMethod.invoke(var1x)), o));
            }
        }

    }

    public static void sendPacketExclude(Collection<? extends Player> players, Player player, Object... obj) {
        for(int i = 0; i < obj.length; i++) {
            Object o = obj[i];
            if (o != null) {
                players.stream().filter((var1x) -> var1x != null && player != null && !var1x.equals(player)).forEach((var1x) -> sendPacket.invoke(playerConnectionField.get(getHandleMethod.invoke(var1x)), o));
            }
        }

    }

    public static void sendPacketExclude(Player p, Object... obj) {
        for(int i = 0; i < obj.length; i++) {
            Object o = obj[i];
            if (o != null) {
                Bukkit.getOnlinePlayers().stream().filter((var1x) -> !var1x.equals(p)).forEach((var1x) -> sendPacket.invoke(playerConnectionField.get(getHandleMethod.invoke(var1x)), o));
            }
        }

    }

    public static void sendPacketExclude(List<UUID> uuids, Object... obj) {
        for(int i = 0; i < obj.length; i++) {
            Object o = obj[i];
            if (o != null) {
                Bukkit.getOnlinePlayers().stream().filter((var1x) -> !uuids.contains(var1x.getUniqueId())).forEach((var1x) -> sendPacket.invoke(playerConnectionField.get(getHandleMethod.invoke(var1x)), o));
            }
        }

    }

    public static List<Object> sendConstantPacketObj(World w, Object... obj) {
        sendPacket(w, obj);
        addPacket(obj);
        return new ArrayList<>(Arrays.asList(obj));
    }
    public static void sendConstantPacket(World w, Object... obj) {
        sendPacket(w, obj);
        addPacket(obj);
    }

    public static List<Object> sendConstantPacketNearby(Location var0, Object... var1) {
        sendPacketNearby(var0, var1);
        addPacket(var1);
        return new ArrayList<>(Arrays.asList(var1));
    }

    public static void sendPacketNearby(Location var0, Object... var1) {
        UtilLocation.getClosestPlayersFromLocation(var0, 64.0f).forEach((var1x) -> sendPacket(var1x, var1));
    }

    public static void equipPlayerPacket(Entity ent, int i, ItemStack stack) {
        Object o = packetEquipConstructor.invoke();
        IDPacketEquipField.set(o, getIdMethod.invoke(getHandleMethod.invoke(ent)));
        enumItemSlotPacketEquipField.set(o, enumItemSlotClass.getEnumConstants()[i]);
        itemStackPacketEquipField.set(o, asNMSCopyMethod.invoke(craftItemStackClass, stack));
        sendPacket(ent.getWorld(), o);
        //  if (b) {
        addPacket(o);
        //   }

    }

    public static void equipPlayer(Entity ent, int i, ItemStack stack) {
        Object o = packetEquipConstructor.invoke();
        IDPacketEquipField.set(o, getIdMethod.invoke(getHandleMethod.invoke(ent)));
        enumItemSlotPacketEquipField.set(o, enumItemSlotClass.getEnumConstants()[i]);
        itemStackPacketEquipField.set(o, asNMSCopyMethod.invoke(craftItemStackClass, stack));
        sendPacket(ent.getWorld(), o);
    }

    public static Object getHandle(Player var0) {
        return getHandlePlayerMethod.invoke(var0);
    }

    public static void respawnPlayer(Player p, Player p1) {
        sendPacket(p1, packetSpawnHumanConstructor.invoke(getHandleMethod.invoke(p)));
        equipPlayer(p, 0, p.getInventory().getItemInMainHand());
        equipPlayer(p, 1, p.getInventory().getItemInOffHand());
        equipPlayer(p, 2, p.getInventory().getBoots());
        equipPlayer(p, 3, p.getInventory().getLeggings());
        equipPlayer(p, 4, p.getInventory().getChestplate());
        equipPlayer(p, 5, p.getInventory().getHelmet());
    }

    public static void destroyEntity(World w, int... ints) {
        Object obj = packetDestroyConstructor.invoke();
        packetDestroyIDField.set(obj, ints);
        sendPacket(w, obj);
    }
    public static void destroyEntity(Player p, int... ints) {
        Object obj = packetDestroyConstructor.invoke();
        packetDestroyIDField.set(obj, ints);
        sendPacket(p, obj);
    }

    public static String getPlayerLanguage(Player var0) {
        return Reflection.getField(Reflection.getMinecraftClass("EntityPlayer"), "locale", String.class).get(getHandle(var0));
    }

    public static void addPacket(Object... var0) {
        packetsToSend.addAll(Arrays.asList(var0));
    }

    public static Map<UUID, List<Object>> getPacketsToSendToOthers() {
        return packetsToSendToOthers;
    }

    public static List<Object> getPacketsToSend() {
        return packetsToSend;
    }

    public static Map<UUID, List<Object>> getPacketsToSendPlayer() {
        return packetsToSendPlayer;
    }
}
