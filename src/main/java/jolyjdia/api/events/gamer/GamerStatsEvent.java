package jolyjdia.api.events.gamer;

import jolyjdia.api.player.GamePlayer;

public class GamerStatsEvent extends GamerEvent {

    public GamerStatsEvent(GamePlayer gamer) {
        super(gamer);
    }
}
