package jolyjdia.nms.interfaces;

import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import jolyjdia.api.skin.Skin;
import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.entity.DEntityPlayer;
import jolyjdia.nms.interfaces.gui.DEnchantingTable;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface NmsManager {

    <T extends DEntity> T createDEntity(Class<T> classEntity, Location location);

    DEntityPlayer createEntityPlayer(World world, GameProfile gameProfile);

    DWorldBorder createBorder(World world);

    void updateScaledHealth(Player player);

    void triggerHealthUpdate(Player player);

    void updateAbilities(Player player);

    int getEntityID(Entity entity);

    GameProfile getGameProfile(Player player);

    int getItemID(ItemStack itemStack);

    int getPing(Player player);

    void sendCrashClientPacket(Player target);

    Channel getChannel(Player player);

    DEnchantingTable getEnchantingTable(Player player);

    void setSkin(Player player, Skin skin);

    void sendPacket(Player player, Object packet);

    void disableFire(Player player);

    //List<Block> getBlocksBelow(Player player); //todo возможно из-за этого не ломаются блоки, проверить

    void playChestAnimation(Block chest, boolean open);

    void removeArrowFromPlayer(Player player);

    void stopReadingPacket(Player player);

    /**
     * переслать метадату про энтити
     * @param player - кому слать
     * @param entity - про какое энтити
     */
    void sendMetaData(Player player, Entity entity);
    void sendGlowing(Player player, Entity who, boolean glow);

    void launchInstantFirework(Firework firework);


}
