package jolyjdia.scoreboard.objective;

import jolyjdia.Main;
import jolyjdia.api.boards.CustomObjective;
import jolyjdia.api.entity.CraftGamer;
import jolyjdia.nms.interfaces.packet.DPacket;
import jolyjdia.nms.interfaces.packet.PacketContainer;
import jolyjdia.nms.interfaces.packet.scoreboard.PacketDisplayObjective;
import jolyjdia.nms.interfaces.packet.scoreboard.PacketScoreboardObjective;
import jolyjdia.nms.scoreboard.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CraftObjective implements CustomObjective {
    private static final PacketContainer PACKET_CONTAINER = Main.PACKET_CONTAINER;
    private final ObjectiveManager objectiveManager;
    private final DObjective objective;
    private DisplaySlot slot;
    private boolean vision;
    @Nullable private Player owner;
    private final Set<Player> players = new HashSet<>();

    public CraftObjective(ObjectiveManager objectiveManager, String name, String value, ObjectiveType type) {
        this.objectiveManager = objectiveManager;
        this.objective = new DObjective(name, value, type);
        this.slot = DisplaySlot.BELOW_NAME;
        this.vision = false;
        this.owner = null;
        this.objectiveManager.addObjective(this);
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public Collection<? extends Player> getVisiblePlayers() {
        if (vision) {
            return Bukkit.getOnlinePlayers();
        }
        return players;
    }


    @Override
    public void showTo(Player player) {
        if (players.contains(player)) {
            return;
        }

        players.add(player);

        PacketScoreboardObjective packet1 = PACKET_CONTAINER.getScoreboardObjectivePacket(objective, ObjectiveActionMode.CREATE);
        PacketDisplayObjective packet2 = PACKET_CONTAINER.getDisplayObjectivePacket(slot, objective);
        PACKET_CONTAINER.sendPacket(player, packet1, packet2);
    }

    @Override
    public void showTo(CraftGamer gamer) {
        if (gamer == null) {
            return;
        }

        Player player = gamer.getPlayer();
        if (player == null || !player.isOnline()) {
            return;
        }

        showTo(player);
    }

    @Override
    public void removeTo(CraftGamer gamer) {
        if (gamer == null) {
            return;
        }

        Player player = gamer.getPlayer();
        if (player == null) { //нельзя проверять на isOnline, тк это при выходе игрока делается и будет true
            return;
        }

        removeTo(player);
    }

    @Override
    public boolean isVisibleTo(Player player) {
        return players.contains(player.getUniqueId());
    }

    @Override
    public void removeTo(Player player) {
        if (!players.remove(player.getUniqueId())) {
            return;
        }

        PACKET_CONTAINER.getScoreboardObjectivePacket(objective, ObjectiveActionMode.REMOVE).sendPacket(player);
    }

    @Override
    public void hideAll() {
        setPublic(true);
        Bukkit.getOnlinePlayers().forEach(this::removeTo);
    }

    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
        showTo(owner);
    }

    public DObjective getDObjective() {
        return objective;
    }

    @Override
    public boolean isPublic() {
        return vision;
    }

    @Override
    public void setPublic(boolean vision) {
        this.vision = vision;

        if (vision) {
            PacketScoreboardObjective packet1 = PACKET_CONTAINER.getScoreboardObjectivePacket(objective, ObjectiveActionMode.CREATE);
            PacketDisplayObjective packet2 = PACKET_CONTAINER.getDisplayObjectivePacket(slot, objective);
            sendPacket(packet1);
            sendPacket(packet2);
            return;
        }

        PacketScoreboardObjective packet = PACKET_CONTAINER.getScoreboardObjectivePacket(objective,
                ObjectiveActionMode.REMOVE);
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (!players.contains(p.getUniqueId())) {
                packet.sendPacket(p);
            }
        });
    }

    @Override
    public void setDisplayName(String displayName) {
        objective.setDisplayName(displayName);
        sendPacket(PACKET_CONTAINER.getScoreboardObjectivePacket(objective,
                ObjectiveActionMode.UPDATE));
    }

    @Override
    public void setDisplaySlot(DisplaySlot displaySlot) {
        slot = displaySlot;
        sendPacket(PACKET_CONTAINER.getDisplayObjectivePacket(displaySlot, objective));
    }

    @Override
    public void setScore(Player player, int score) {
        if (!vision && !isVisibleTo(player)) {
            return;
        }
        DScore dScore = new DScore(player.getName(), objective, score);
        sendPacket(PACKET_CONTAINER.getScoreboardScorePacket(dScore, ScoreboardAction.CHANGE));
    }

    @Override
    public void remove() {
        objectiveManager.removeObjective(this);
        PacketScoreboardObjective packet = PACKET_CONTAINER.getScoreboardObjectivePacket(objective, ObjectiveActionMode.REMOVE);
        sendPacket(packet);
    }

    private void sendPacket(DPacket packet) {
        if (vision) {
            Bukkit.getOnlinePlayers().forEach(packet::sendPacket);
            return;
        }
        players.forEach(packet::sendPacket);
    }
}
