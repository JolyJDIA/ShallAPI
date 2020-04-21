package jolyjdia.api.events.gamer;

import jolyjdia.api.player.GamePlayer;

public class GamerUpdateGroupEvent extends GamerEvent {
    public GamerUpdateGroupEvent(GamePlayer gamer) {
        super(gamer);
    }
}
