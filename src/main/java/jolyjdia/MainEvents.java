package jolyjdia;

import jolyjdia.api.boards.ScoreBoardAPI;
import jolyjdia.netty.packets.client.ClientGetGroupPacket;
import jolyjdia.scoreboard.ScoreBoardAPIImpl;
import org.bukkit.entity.Player;
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
        ClientGetGroupPacket packet = new ClientGetGroupPacket();
        packet.setUuid(e.getPlayer().getUniqueId());
        packet.sendPacket(main.getChannel());
    }

    @EventHandler
    public static void onQuit(@NotNull PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player player = e.getPlayer();
        SCORE_BOARD_API.removeDefaultTag(player);
    }
}
