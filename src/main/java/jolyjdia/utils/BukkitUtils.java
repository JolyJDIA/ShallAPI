package jolyjdia.utils;

import jolyjdia.Main;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
    public static Color getColour(final int c) {
        switch (c) {
            case 0 -> {
                return Color.WHITE;
            }
            case 1 -> {
                return Color.SILVER;
            }
            case 2 -> {
                return Color.GRAY;
            }
            case 3 -> {
                return Color.RED;
            }
            case 4 -> {
                return Color.MAROON;
            }
            case 5 -> {
                return Color.YELLOW;
            }
            case 6 -> {
                return Color.OLIVE;
            }
            case 7 -> {
                return Color.LIME;
            }
            case 8 -> {
                return Color.GREEN;
            }
            case 9 -> {
                return Color.AQUA;
            }
            case 10 -> {
                return Color.TEAL;
            }
            case 11 -> {
                return Color.BLUE;
            }
            case 12 -> {
                return Color.NAVY;
            }
            case 13 -> {
                return Color.FUCHSIA;
            }
            case 14 -> {
                return Color.PURPLE;
            }
            case 15 -> {
                return Color.ORANGE;
            }
        }
        return Color.WHITE;
    }
    public static void runSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(Main.getInstance(), runnable);
    }
    public static void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), runnable);
    }
    public static void callSyncEvent(Event event) {
        runSync(() -> Bukkit.getPluginManager().callEvent(event));
    }
}
