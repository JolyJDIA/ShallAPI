package jolyjdia.api.events.gamer;

import jolyjdia.api.player.GamePlayer;

public class GamerChangeStatsEvent extends GamerEvent {

    public GamerChangeStatsEvent(GamePlayer gamer) {
        super(gamer);
    }
}
