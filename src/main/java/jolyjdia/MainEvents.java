package jolyjdia;

import jolyjdia.api.boards.ScoreBoardAPI;
import jolyjdia.connector.packets.client.ClientGetBaseDataPacket;
import jolyjdia.scoreboard.ScoreBoardAPIImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class MainEvents implements Listener {
    public static final ScoreBoardAPI SCORE_BOARD_API = new ScoreBoardAPIImpl();

    private final Main main;
    public MainEvents(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(@NotNull PlayerJoinEvent e) {
        e.setJoinMessage(null);
        ClientGetBaseDataPacket packet = new ClientGetBaseDataPacket(e.getPlayer().getUniqueId());
        packet.sendPacket(main.getChannel());
    }

    @EventHandler
    public static void onQuit(@NotNull PlayerQuitEvent e) {
        e.setQuitMessage(null);
        SCORE_BOARD_API.removeDefaultTag(e.getPlayer());
    }
}
