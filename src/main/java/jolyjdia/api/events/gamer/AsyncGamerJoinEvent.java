package jolyjdia.api.events.gamer;

import jolyjdia.api.player.GamePlayer;

@Deprecated
public class AsyncGamerJoinEvent extends GamerEvent {

    public AsyncGamerJoinEvent(GamePlayer gamer) {
        super(gamer, true);
    }
}
