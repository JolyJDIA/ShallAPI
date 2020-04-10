package jolyjdia.scoreboard.board;

import jolyjdia.api.boards.Board;
import org.bukkit.entity.Player;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.UUID;

public class BoardManager {
    private final Map<UUID, CraftBoard> boards = new IdentityHashMap<>();

    final void addBoard(Player player, CraftBoard board) {
        Board check = boards.remove(player.getUniqueId());
        if (check != null) {
            check.remove();
        }
        boards.put(player.getUniqueId(), board);
    }

    void removeBoard(Player player) {
        boards.remove(player.getUniqueId());
    }

    public Map<UUID, CraftBoard> getPlayersBoard() {
        return boards;
    }
}
