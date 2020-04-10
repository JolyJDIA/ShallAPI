package jolyjdia.nms.v1_15_R1.entity;

import com.mojang.authlib.GameProfile;
import jolyjdia.nms.interfaces.entity.DEntityPlayer;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.PlayerInteractManager;
import net.minecraft.server.v1_15_R1.WorldServer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DEntityPlayerImpl extends DEntityLivingBase<EntityPlayer> implements DEntityPlayer {

    public DEntityPlayerImpl(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile,
                             PlayerInteractManager manager) {
        super(new EntityPlayer(minecraftserver, worldserver, gameprofile, manager));
    }

    @Override
    public String getName() {
        return entity.getName();
    }

    @Override
    public GameProfile getProfile() {
        return entity.getProfile();
    }

    @Override
    public ItemStack getItemInHand() {
        return new ItemStack(Material.AIR); //nothing
    }

    @Override
    public int getPing() {
        return entity.ping;
    }
}
