package jolyjdia.scoreboard.board;

import jolyjdia.Main;
import jolyjdia.api.boards.Board;
import jolyjdia.api.boards.BoardLine;
import jolyjdia.api.entity.CraftGamer;
import jolyjdia.nms.interfaces.packet.scoreboard.PacketDisplayObjective;
import jolyjdia.nms.interfaces.packet.scoreboard.PacketScoreBoardTeam;
import jolyjdia.nms.interfaces.packet.scoreboard.PacketScoreboardObjective;
import jolyjdia.nms.interfaces.packet.scoreboard.PacketScoreboardScore;
import jolyjdia.nms.scoreboard.*;
import jolyjdia.scoreboard.board.lines.CraftBoardLine;
import jolyjdia.scoreboard.board.lines.DisplayNameLine;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jolyjdia.Main.PACKET_CONTAINER;

public class CraftBoard implements Board {
    private final BoardManager boardManager;
    private final DisplayNameLine displayNameLine;
    private final Map<Runnable, Long> tasks = new HashMap<>();//todo: static
    public final Map<Integer, CraftBoardLine> lines = new HashMap<>();
    private Player owner;
    private boolean active;

    public CraftBoard(BoardManager boardManager) {
        this.boardManager = boardManager;
        this.displayNameLine = new DisplayNameLine();
        this.active = false;
    }

    @Override
    public void showTo(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }
        owner = player;
        boardManager.addBoard(player, this);
        active = true;

        PacketScoreboardObjective packet1 = PACKET_CONTAINER.getScoreboardObjectivePacket(
                displayNameLine.getdObjective(), ObjectiveActionMode.CREATE);
        PacketDisplayObjective packet2 = PACKET_CONTAINER.getDisplayObjectivePacket(
                DisplaySlot.SIDEBAR, displayNameLine.getdObjective());

        PACKET_CONTAINER.sendPacket(player, packet1, packet2);
        displayNameLine.setPlayerName(player);

        lines.forEach((integer, craftBoardLine) -> sendLine(craftBoardLine, integer, true));

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            for (Runnable runnable : tasks.keySet()) {
                runnable.run();
            }
        });
    }

    @Override
    public void showTo(CraftGamer gamer) {
        showTo(gamer.getPlayer());
    }

    public boolean isActive() {
        return active && owner != null && owner.isOnline();
    }

    public void sendLine(CraftBoardLine craftBoardLine, int number, boolean sendTeam) {
        if (owner == null) {
            return;
        }
        DScore dScore = new DScore(craftBoardLine.getText(), displayNameLine.getdObjective(), number);

        PacketScoreboardScore packet = PACKET_CONTAINER.getScoreboardScorePacket(dScore, ScoreboardAction.CHANGE);
        packet.sendPacket(owner);

        if (craftBoardLine.getTeam() != null && sendTeam) {
            DTeam team = craftBoardLine.getTeam();
            PacketScoreBoardTeam packet2 = PACKET_CONTAINER.getScoreBoardTeamPacket(team, TeamAction.CREATE);
            packet2.sendPacket(owner);
        }
    }

    @Override
    public Map<Integer, BoardLine> getLines() {
        return new HashMap<>(lines);
    }

    @Override
    public int getSize() {
        return lines.size();
    }

    @Nullable
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setDisplayName(String name) {
        displayNameLine.setName(name);
    }

    @Override
    public void setDynamicDisplayName(String name) {
        displayNameLine.setNames(name);
        displayNameLine.setSpeed(1);
    }

    @Override
    public void setDynamicDisplayName(List<String> lines, long speed) {
        if (lines.isEmpty()) {
            throw new IllegalStateException("Список анимаций не может быть пустым!");
        }

        displayNameLine.setNames(lines, lines.get(0));
        displayNameLine.setSpeed(speed);
    }

    @Override
    public void setLine(int slot, String line) {
        if (line == null) {
            line = "§cError";
        }

        removeLine(slot);
        CraftBoardLine craftBoardLine = new CraftBoardLine(this, slot, line, false);
        sendLine(craftBoardLine, slot, true);
        lines.put(slot, craftBoardLine);
    }

    @Override
    public void setDynamicLine(int number, String notChangeText, String change) {
        if (notChangeText.length() > 32) {
            throw new IllegalStateException("Text value too big");
        }

        if (notChangeText.length() > 16) {
            createDynamicLine(number, notChangeText.substring(0, 16), notChangeText.substring(16), change);
        } else {
            createDynamicLine(number, "", notChangeText, change);
        }
    }

    public void createDynamicLine(int number, String pref, String name, String suffix) {
        CraftBoardLine craftBoardLine = lines.get(number);
        PacketScoreBoardTeam packet;

        if (craftBoardLine == null || !craftBoardLine.getText().equals(name)) {
            removeLine(number);
            CraftBoardLine boardLine = new CraftBoardLine(this, number, name, true);
            lines.put(number, boardLine);
            sendLine(boardLine, number, false);

            DTeam team = new DTeam(String.valueOf(number));
            team.addPlayer(name);
            team.setPrefix(pref);
            team.setSuffix(suffix);
            boardLine.setTeam(team);

            packet = PACKET_CONTAINER.getScoreBoardTeamPacket(team, TeamAction.CREATE);
        } else {
            DTeam team = craftBoardLine.getTeam();
            if (craftBoardLine.getTeam() == null) {
                team = new DTeam(String.valueOf(number));
                team.addPlayer(name);
                craftBoardLine.setTeam(team);
            }
            team.setPrefix(pref);
            team.setSuffix(suffix);
            packet = PACKET_CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE);
        }

        if (owner == null) {
            return;
        }
        packet.sendPacket(owner);
    }

    @Override
    public void updater(Runnable runnable, long speed) {
        tasks.put(runnable, speed);
    }

    @Override
    public void updater(Runnable runnable) {
        updater(runnable, 20);
    }

    @Override
    public void removeUpdater(Runnable runnable) {
        tasks.remove(runnable);
    }

    @Override
    public void removeLine(int number) {
        CraftBoardLine craftBoardLine = lines.remove(number);
        if (craftBoardLine == null) {
            return;
        }
        DScore dScore = new DScore(craftBoardLine.getText(), displayNameLine.getdObjective(), number);
        PacketScoreboardScore packet = PACKET_CONTAINER.getScoreboardScorePacket(dScore,
                ScoreboardAction.REMOVE);

        if (owner == null) {
            return;
        }
        packet.sendPacket(owner);

        if (craftBoardLine.getTeam() == null) {
            return;
        }
        PacketScoreBoardTeam teamPacket = PACKET_CONTAINER.getScoreBoardTeamPacket(craftBoardLine.getTeam(),
                TeamAction.REMOVE);
        teamPacket.sendPacket(owner);
    }

    @Override
    public void remove() {
        if (owner == null) {
            return;
        }
        boardManager.removeBoard(owner);
        active = false;

        for (int line : lines.keySet()) {
            removeLine(line);
        }
        DObjective objective = displayNameLine.getdObjective();
        PacketScoreboardObjective packet = PACKET_CONTAINER.getScoreboardObjectivePacket(objective, ObjectiveActionMode.REMOVE);
        packet.sendPacket(owner);
    }

    public DisplayNameLine getDisplayNameLine() {
        return displayNameLine;
    }

    public Map<Runnable, Long> getTasks() {
        return tasks;
    }
}
