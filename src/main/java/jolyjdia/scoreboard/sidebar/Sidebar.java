package jolyjdia.scoreboard.sidebar;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jolyjdia.scoreboard.wrapper.AbstractPacket;
import jolyjdia.scoreboard.wrapper.WrapperPlayServerScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Sidebar {

    private final Set<UUID> players = new HashSet<>();
    private final Map<Integer, SidebarLine> lines = new HashMap<>();

    private final SidebarObjective objective;

    public Sidebar(String objectiveName, String displayName) {
        this.objective = new SidebarObjective(objectiveName, displayName);

      //  owner.getServer().getPluginManager().registerEvents(this, owner);
    }

    public String getObjectiveName() {
        return objective.getName();
    }

    public String getDisplayName() {
        return objective.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        objective.setDisplayName(displayName, this);
    }


    public void setLine(int index, String text) {
        SidebarLine line = getLine(index);
        if (line == null) {
            lines.put(index, new SidebarLine(index, text, this));
        } else {
            line.setText(text);
        }
    }

    public SidebarLine getLine(int index) {
        return lines.get(index);
    }

    public Set<UUID> getPlayerUuids() {
        return Collections.unmodifiableSet(players);
    }

    public Map<Integer, SidebarLine> getLines() {
        return Collections.unmodifiableMap(lines);
    }

    public void unregister(UUID playerUuid) {
        unregister(Objects.requireNonNull(Bukkit.getPlayer(playerUuid)));
    }

    public void unregister(@NotNull Player player) {
        Preconditions.checkState(players.contains(player.getUniqueId()),
                "Player %s is not receiving this sidebar.", player.getName());

        lines.values().forEach(line -> {
            line.getTeamPacket(WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED).sendPacket(player);
            line.getScorePacket(EnumWrappers.ScoreboardAction.REMOVE).sendPacket(player);
        });

        objective.remove(player);
        players.remove(player.getUniqueId());
    }

    public void unregisterForAll() {
        players.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(this::unregister);
        players.clear();
    }


    public void send(Player @NotNull ... players) {
        for (Player player : players) {
            Preconditions.checkArgument(!this.players.contains(player.getUniqueId()),
                    "Player %s already receiving this sidebar.", player.getName());
            this.players.add(player.getUniqueId());
            objective.create(player);
            lines.values().forEach(line -> {
                line.getTeamPacket(WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED).sendPacket(player);
                line.getScorePacket(EnumWrappers.ScoreboardAction.CHANGE).sendPacket(player);
            });
            objective.show(player);
        }
    }
    public void send(@NotNull Player player) {
        Preconditions.checkArgument(!this.players.contains(player.getUniqueId()),
                "Player %s already receiving this sidebar.", player.getName());
        this.players.add(player.getUniqueId());
        objective.create(player);
        lines.values().forEach(line -> {
            line.getTeamPacket(WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED).sendPacket(player);
            line.getScorePacket(EnumWrappers.ScoreboardAction.CHANGE).sendPacket(player);
        });
        objective.show(player);
    }

    void broadcastPacket(@NotNull AbstractPacket packet) {
        players.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(packet::sendPacket);
    }
}