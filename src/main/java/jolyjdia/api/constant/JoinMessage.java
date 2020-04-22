package jolyjdia.api.constant;

import jolyjdia.api.player.GamePlayer;
import jolyjdia.utils.BukkitUtils;
import jolyjdia.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

public final class JoinMessage {
    private static final String[] MESSAGES = {
            "%s §fпришел кормить",
            "%s §fвсем привет, работяги, всем мое здарова",
            "%s §fприветствует смотрящих",
    };


    private JoinMessage() {}

    //переместить в GamePlayer
    public static void joinMessage(@NotNull GamePlayer gamePlayer) {
        if(gamePlayer.getGroup().getStar() >= 1) {
            String displayName = gamePlayer.getPrefix() + ' '+gamePlayer.getPlayer().getName();
            BukkitUtils.messagesPlayers(String.format(' ' + getMessageRandom(), displayName));
        }
    }
    public static String getMessageRandom() {
        return MESSAGES[MathUtils.RANDOM.nextInt(MESSAGES.length)];
    }
}
