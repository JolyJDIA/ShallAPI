package jolyjdia.scoreboard.board;

import jolyjdia.scoreboard.board.lines.DisplayNameLine;

public class BoardTask implements Runnable {

    private int tick;
    private final BoardManager boardManager;

    public BoardTask(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    @Override
    public final void run() {

        tick = tick == Integer.MAX_VALUE ? 0 : tick;

        for (CraftBoard board : boardManager.getPlayersBoard().values()) {
            if (!board.isActive()) {
                continue;
            }

            DisplayNameLine displayNameLine = board.getDisplayNameLine();
            long speed = displayNameLine.getSpeed();
            if (speed != -1 && displayNameLine.isAnimation()) {
                if (tick != 0 && tick % speed != 0) {
                    continue;
                }
                displayNameLine.updateObjective().run();
            }

            for (Runnable runnable : board.getTasks().keySet()) {
                speed = board.getTasks().get(runnable);
                if (tick != 0 && tick % speed != 0) {
                    continue;
                }

                runnable.run();
            }
        }
        ++tick;
    }
}
