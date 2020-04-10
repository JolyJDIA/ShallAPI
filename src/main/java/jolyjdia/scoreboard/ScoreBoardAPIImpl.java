package jolyjdia.scoreboard;

import jolyjdia.Main;
import jolyjdia.api.boards.Board;
import jolyjdia.api.boards.CustomObjective;
import jolyjdia.api.boards.PlayerTag;
import jolyjdia.api.boards.ScoreBoardAPI;
import jolyjdia.api.permission.Group;
import jolyjdia.nms.scoreboard.ObjectiveType;
import jolyjdia.scoreboard.board.BoardManager;
import jolyjdia.scoreboard.board.BoardTask;
import jolyjdia.scoreboard.board.CraftBoard;
import jolyjdia.scoreboard.objective.CraftObjective;
import jolyjdia.scoreboard.objective.ObjectiveManager;
import jolyjdia.scoreboard.tag.CraftTag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreBoardAPIImpl implements ScoreBoardAPI {
    private CustomObjective tabObjective;
    private Objective healthObjective;

    private final Map<UUID, PlayerTag> tags = new IdentityHashMap<>();

    private final BoardManager boardManager;
    private final ObjectiveManager objectiveManager;

    public ScoreBoardAPIImpl() {
        boardManager = new BoardManager();
        objectiveManager = new ObjectiveManager();
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new BoardTask(boardManager), 0, 1L);
    }

    @Override
    public Board createBoard() {
        return new CraftBoard(boardManager);
    }

    @Override
    public CustomObjective createObjective(String name, String value) {
        return new CraftObjective(objectiveManager, name, value, ObjectiveType.INTEGER);
    }

    @Override
    public PlayerTag createTag(String name) {
        return new CraftTag(name);
    }

    @Override
    public void createGameObjective(boolean health, boolean tab) {
        if (health) {
            if (healthObjective != null) {
                healthObjective.unregister();
            }
            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            if(scoreboardManager == null) {
                return;
            }
            healthObjective = scoreboardManager.getMainScoreboard().registerNewObjective("showhealth", "health", "§4❤");
            healthObjective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME);

            Bukkit.getOnlinePlayers().forEach(player ->
                    healthObjective.getScore(player.getName())
                            .setScore((int)player.getHealth()));
        }

        if (tab) {
            if (tabObjective != null) {
                tabObjective.remove();
            }
            tabObjective = createObjective("playerlist", "stat");
            tabObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
            tabObjective.setPublic(true);
        }
    }

    @Override
    public void setScoreTab(Player player, int score) {
        if (player == null || tabObjective == null) {
            return;
        }

        tabObjective.setScore(player, score);
    }

    @Override
    public void unregisterObjectives() {
        if (tabObjective != null) {
            tabObjective.remove();
            tabObjective = null;
        }

        if (healthObjective != null) {
            healthObjective.unregister();
            healthObjective = null;
        }
    }

    @Override
    public Map<UUID, PlayerTag> getActiveDefaultTag() {
        return Collections.unmodifiableMap(tags);
    }

    @Override
    public void setDefaultTag(Player player, PlayerTag playerTag) {
        tags.values().forEach(tag -> tag.sendTo(player));
        tags.put(player.getUniqueId(), playerTag);
        playerTag.sendToAll();
    }

    @Override
    public void removeDefaultTag(Player player) {
        PlayerTag playerTag = tags.remove(player.getUniqueId());
        if (playerTag == null) {
            return;
        }

        playerTag.remove();
    }

    @Override
    public void removeDefaultTags() {
        tags.values().forEach(PlayerTag::remove);
        tags.clear();
    }

    @Override
    public void setPrefix(Player player, String prefix) {
        PlayerTag playerTag = tags.get(player.getUniqueId());
        if (playerTag == null) {
            return;
        }

        playerTag.setPrefix(prefix);
        /*if (GameState.getCurrent() == GameState.GAME) {
            return;
        }*/

        playerTag.sendToAll();
    }

    @Override
    public void setSuffix(Player player, String suffix) {
        PlayerTag playerTag = tags.get(player.getUniqueId());
        if (playerTag == null) {
            return;
        }

        playerTag.setSuffix(suffix);
        /*if (GameState.getCurrent() == GameState.GAME) {
            return;
        }*/

        playerTag.sendToAll();
    }

    @Override
    public void removeBoard(Player player) {
        Board board = getBoard(player);
        if (board == null) {
            return;
        }

        board.remove();
    }

    @Override
    public Board getBoard(Player player) {
        return boardManager.getPlayersBoard().get(player.getUniqueId());
    }

    @Override
    public Map<UUID, Board> getActiveBoards() {
        return Collections.unmodifiableMap(boardManager.getPlayersBoard());
    }
    @Override
    public int getPriorityScoreboardTag(Group group) {
        return group.getLevel();
    }
}
