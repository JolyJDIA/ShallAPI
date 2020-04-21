package jolyjdia.api.events.gamer;

import jolyjdia.api.player.GamePlayer;

public class GamerJoinEvent extends GamerEvent {

    public GamerJoinEvent(GamePlayer gamer) {
        super(gamer);
    }
}
