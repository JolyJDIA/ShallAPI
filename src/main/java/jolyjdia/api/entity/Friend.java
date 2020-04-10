package jolyjdia.api.entity;

import jolyjdia.api.player.IBaseGamer;

public interface Friend {

    int getPlayerId();

    boolean isOnline();

    <T extends IBaseGamer> T getGamer();
}
