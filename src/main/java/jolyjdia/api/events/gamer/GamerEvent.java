package jolyjdia.api.events.gamer;

import jolyjdia.api.events.JEvent;
import jolyjdia.api.player.GamePlayer;

public class GamerEvent extends JEvent {

    private final GamePlayer gamer;

    public GamerEvent(GamePlayer gamer) {
        this(gamer, false);
    }

    public GamerEvent(GamePlayer gamer, boolean async) {
        super(async);
        this.gamer = gamer;
    }

    public GamePlayer getGamer() {
        return gamer;
    }
}
