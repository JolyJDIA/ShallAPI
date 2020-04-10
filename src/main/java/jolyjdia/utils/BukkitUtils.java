package jolyjdia.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@NonNls public final class BukkitUtils {

    private BukkitUtils() {}

    public static void unregisterTag(@NotNull Player p) {
        Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Team t = sb.getTeam(p.getName());
        if (t == null) {
            return;
        }
        t.unregister();
    }
    public static void messagesPlayers(String msg) {
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(msg));
    }
}
