package jolyjdia.nms.scoreboard;

public enum TeamAction {
    CREATE(0),
    REMOVE(1),
    UPDATE(2),
    PLAYERS_ADD(3),
    PLAYERS_REMOVE(4);

    private final int mode;

    TeamAction(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }
}
