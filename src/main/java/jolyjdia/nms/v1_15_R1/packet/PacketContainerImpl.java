package jolyjdia.nms.v1_15_R1.packet;

import jolyjdia.api.craftentity.npc.AnimationNpcType;
import jolyjdia.nms.interfaces.DWorldBorder;
import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.entity.DEntityLiving;
import jolyjdia.nms.interfaces.entity.DEntityPlayer;
import jolyjdia.nms.interfaces.packet.DPacket;
import jolyjdia.nms.interfaces.packet.PacketContainer;
import jolyjdia.nms.interfaces.packet.entity.*;
import jolyjdia.nms.interfaces.packet.entityplayer.PacketCamera;
import jolyjdia.nms.interfaces.packet.entityplayer.PacketNamedEntitySpawn;
import jolyjdia.nms.interfaces.packet.entityplayer.PacketPlayerInfo;
import jolyjdia.nms.interfaces.packet.scoreboard.PacketDisplayObjective;
import jolyjdia.nms.interfaces.packet.scoreboard.PacketScoreBoardTeam;
import jolyjdia.nms.interfaces.packet.scoreboard.PacketScoreboardObjective;
import jolyjdia.nms.interfaces.packet.scoreboard.PacketScoreboardScore;
import jolyjdia.nms.scoreboard.*;
import jolyjdia.nms.types.EntitySpawnType;
import jolyjdia.nms.types.PlayerInfoActionType;
import jolyjdia.nms.types.TitleActionType;
import jolyjdia.nms.types.WorldBorderActionType;
import jolyjdia.nms.v1_15_R1.DWorldBorderImpl;
import jolyjdia.nms.v1_15_R1.packet.entity.*;
import jolyjdia.nms.v1_15_R1.packet.entityplayer.PacketCameraImpl;
import jolyjdia.nms.v1_15_R1.packet.entityplayer.PacketNamedEntitySpawnImpl;
import jolyjdia.nms.v1_15_R1.packet.entityplayer.PacketPlayerInfoImpl;
import jolyjdia.nms.v1_15_R1.packet.scoreboard.PacketDisplayObjectiveImpl;
import jolyjdia.nms.v1_15_R1.packet.scoreboard.PacketScoreBoardTeamImpl;
import jolyjdia.nms.v1_15_R1.packet.scoreboard.PacketScoreboardObjectiveImpl;
import jolyjdia.nms.v1_15_R1.packet.scoreboard.PacketScoreboardScoreImpl;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;

public class PacketContainerImpl implements PacketContainer {

    @Override
    public void sendPacket(final Player player, DPacket... dPackets) {
        if (dPackets.length == 0) {
            return;
        }
        for(DPacket dPacket : dPackets) {
            dPacket.sendPacket(player);
        }
    }

    @Override
    public void sendChatPacket(Player player, String message,
                               ChatMessageType messageType) {
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer
                .a(messageType == ChatMessageType.GAME_INFO ? "{\"text\": \"" + message + "\"}" : message));
        sendPacket(player, packetPlayOutChat);
    }

    @Override
    public void sendTitlePacket(Player player, TitleActionType type, String message) {
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.valueOf(type.name()),
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"));
        sendPacket(player, packet);
    }

    @Override
    public void sendTitlePacket(Player player, int fadeIn, int stay, int fadeOut) {
        PacketPlayOutTitle packet = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
        sendPacket(player, packet);
    }

    @Override
    public void sendWorldBorderPacket(Player player, DWorldBorder border, WorldBorderActionType type) {
        WorldBorder worldBorder = ((DWorldBorderImpl)border).get();
        PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder,
                PacketPlayOutWorldBorder.EnumWorldBorderAction.valueOf(type.name()));
        sendPacket(player, packet);
    }

    @Override
    public PacketScoreBoardTeam getScoreBoardTeamPacket(DTeam team, TeamAction action) {
        return new PacketScoreBoardTeamImpl(team, action);
    }

    @Override
    public PacketDisplayObjective getDisplayObjectivePacket(DisplaySlot slot, DObjective objective) {
        return new PacketDisplayObjectiveImpl(slot, objective);
    }

    @Override
    public PacketScoreboardObjective getScoreboardObjectivePacket(DObjective objective, ObjectiveActionMode mode) {
        return new PacketScoreboardObjectiveImpl(objective, mode);
    }

    @Override
    public PacketScoreboardScore getScoreboardScorePacket(DScore score, ScoreboardAction action) {
        return new PacketScoreboardScoreImpl(score, action);
    }

    @Override
    public PacketNamedEntitySpawn getNamedEntitySpawnPacket(DEntityPlayer entityPlayer) {
        return new PacketNamedEntitySpawnImpl(entityPlayer);
    }

    @Override
    public PacketPlayerInfo getPlayerInfoPacket(DEntityPlayer entityPlayer, PlayerInfoActionType actionType) {
        return new PacketPlayerInfoImpl(entityPlayer, actionType);
    }

    private void sendPacket(Player player, Packet<?> packet) {
        if (player == null || !player.isOnline())
            return;

        EntityPlayer handle = ((CraftPlayer) player).getHandle();
        if (handle == null)
            return;

        PlayerConnection playerConnection = handle.playerConnection;
        if (playerConnection == null)
            return;

        playerConnection.sendPacket(packet);
    }

    @Override
    public PacketAnimation getAnimationPacket(DEntity entity, AnimationNpcType animation) {
        return new PacketAnimationImpl(entity, animation);
    }

    @Override
    public PacketAttachEntity getAttachEntityPacket(DEntity entity, DEntity vehicle) {
        return new PacketAttachEntityImpl(entity, vehicle);
    }

    @Override
    public PacketEntityDestroy getEntityDestroyPacket(int... entityIDs) {
        return new PacketEntityDestroyImpl(entityIDs);
    }

    @Override
    public PacketMount getMountPacket(DEntity entity) {
        return new PacketMountImpl(entity);
    }

    @Override
    public PacketEntityMetadata getEntityMetadataPacket(DEntity entity) {
        return new PacketEntityMetadataImpl(entity);
    }

    @Override
    public PacketCamera getCameraPacket(Player player) {
        return new PacketCameraImpl(player);
    }

    @Override
    public PacketEntityLook getEntityLookPacket(DEntity entity, byte yaw, byte pitch) {
        return new PacketEntityLookImpl(entity, yaw, pitch);
    }

    @Override
    public PacketEntityEquipment getEntityEquipmentPacket(DEntity entity, EquipmentSlot slot, ItemStack itemStack) {
        return new PacketEntityEquipmentImpl(entity, slot, itemStack);
    }

    @Override
    public PacketEntityHeadRotation getEntityHeadRotationPacket(DEntity entity, byte yaw) {
        return new PacketEntityHeadRotationImpl(entity, yaw);
    }

    @Override
    public PacketSpawnEntity getSpawnEntityPacket(DEntity entity, EntitySpawnType entitySpawnType, int objectData) {
        return new PacketSpawnEntityImpl(entity, entitySpawnType, objectData);
    }

    @Override
    public PacketSpawnEntityLiving getSpawnEntityLivingPacket(DEntityLiving entityLiving) {
        return new PacketSpawnEntityLivingImpl(entityLiving);
    }

    @Override
    public PacketEntityTeleport getEntityTeleportPacket(DEntity entity) {
        return new PacketEntityTeleportImpl(entity);
    }
}
