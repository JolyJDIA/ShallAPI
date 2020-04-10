package jolyjdia.scoreboard.board.lines;

import jolyjdia.api.boards.Board;
import jolyjdia.api.boards.BoardLine;
import jolyjdia.nms.scoreboard.DTeam;
import jolyjdia.scoreboard.board.CraftBoard;

public class CraftBoardLine implements BoardLine {
    private final CraftBoard board;
    private int slot;
    private DTeam team;
    private final String text;
    private final boolean dynamic;

    public CraftBoardLine(CraftBoard board, int slot, String text, boolean dynamic) {
        this.board = board;
        this.slot = slot;
        this.text = text;
        this.dynamic = dynamic;
    }

    public CraftBoardLine(CraftBoard board, int slot, String text, boolean dynamic, DTeam team) {
        this(board, slot, text, dynamic);
        this.team = team;
    }

    public DTeam getTeam() {
        return team;
    }

    public String getText() {
        return text;
    }

    public void setTeam(DTeam team) {
        this.team = team;
    }

    @Override
    public Board getBoard() {
        if (board.isActive()) {
            return board;
        }
        return null;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public void setSlot(int slot) {
        if (board == null)
            return;

        this.slot = slot;
        board.lines.put(slot, this);

        if (dynamic) {
            String pref = team.getPrefix();
            String name = team.getPlayers().get(0);
            String suf = team.getSuffix();

            board.createDynamicLine(slot, pref, name, suf);
            return;
        }

        board.sendLine(this, slot, team != null);
    }

    @Override
    public void remove() {
        if (board == null) {
            return;
        }
        board.removeLine(slot);
    }

    @Override
    public boolean isDynamic() {
        return dynamic;
    }

    @Override
    public String toString() {
        return "CraftBoardLine{" +
                "board=" + board +
                ", number=" + slot +
                ", team=" + team +
                ", text='" + text + '\'' +
                ", dynamic=" + dynamic +
                '}';
    }
}
