package jolyjdia.api.constant;

import jolyjdia.api.player.GamePlayer;

public final class JoinMessage {

    private JoinMessage() {}

    private static final String[] MESSAGES = {
            "%s §fпришел кормить",
            "%s §fвсем привет, работяги, всем мое здарова",
            "%s §fприветствует смотрящих",
    };

    //переместить в GamePlayer
    public static String getRandomJoinMessage(GamePlayer gamePlayer) {
        String displayName = gamePlayer.getPrefix() + ' '+gamePlayer.getPlayer().getName();
        return String.format(' ' + MESSAGES[0], displayName);
    }
}
