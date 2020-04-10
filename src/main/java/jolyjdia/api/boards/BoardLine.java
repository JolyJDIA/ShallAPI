package jolyjdia.api.boards;

public interface BoardLine {

    Board getBoard();

    int getSlot();

    void setSlot(int slot);

    boolean isDynamic();

    void remove();
}
