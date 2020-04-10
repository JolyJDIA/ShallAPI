package jolyjdia.nms.interfaces.packet;

import jolyjdia.api.craftentity.npc.AnimationNpcType;
import jolyjdia.nms.interfaces.DWorldBorder;
import jolyjdia.nms.interfaces.entity.DEntity;
import jolyjdia.nms.interfaces.entity.DEntityLiving;
import jolyjdia.nms.interfaces.entity.DEntityPlayer;
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
import net.minecraft.server.v1_15_R1.ChatMessageType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;

public interface PacketContainer {

    void sendPacket(Player player, DPacket... dPackets);

    void sendChatPacket(Player player, String message, ChatMessageType messageType);

    void sendTitlePacket(Player player, TitleActionType type, String message);
    void sendTitlePacket(Player player, int fadeIn, int stay, int fadeOut);

    void sendWorldBorderPacket(Player player, DWorldBorder border, WorldBorderActionType type);

    PacketScoreBoardTeam getScoreBoardTeamPacket(DTeam team, TeamAction action);

    PacketDisplayObjective getDisplayObjectivePacket(DisplaySlot slot, DObjective objective);

    PacketScoreboardObjective getScoreboardObjectivePacket(DObjective objective, ObjectiveActionMode mode);

    PacketScoreboardScore getScoreboardScorePacket(DScore score, ScoreboardAction action);

    PacketNamedEntitySpawn getNamedEntitySpawnPacket(DEntityPlayer entityPlayer);

    PacketPlayerInfo getPlayerInfoPacket(DEntityPlayer entityPlayer, PlayerInfoActionType actionType);

    PacketAnimation getAnimationPacket(DEntity entity, AnimationNpcType animation);

    PacketAttachEntity getAttachEntityPacket(DEntity entity, DEntity vehicle);

    PacketEntityDestroy getEntityDestroyPacket(int... entityIDs);

    PacketMount getMountPacket(DEntity entity);

    PacketEntityMetadata getEntityMetadataPacket(DEntity entity);

    PacketCamera getCameraPacket(Player player);

    PacketEntityLook getEntityLookPacket(DEntity entity, byte yaw, byte pitch);

    PacketEntityEquipment getEntityEquipmentPacket(DEntity entity, EquipmentSlot slot, ItemStack itemStack);

    PacketEntityHeadRotation getEntityHeadRotationPacket(DEntity entity, byte yaw);

    PacketSpawnEntity getSpawnEntityPacket(DEntity entity, EntitySpawnType entitySpawnType, int objectData);

    PacketSpawnEntityLiving getSpawnEntityLivingPacket(DEntityLiving entityLiving);

    PacketEntityTeleport getEntityTeleportPacket(DEntity entity);
}
