package jolyjdia.actionbar;

import jolyjdia.Main;
import net.minecraft.server.v1_15_R1.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.Collection;

import static jolyjdia.Main.PACKET_CONTAINER;

public class ActionBarAPIImpl implements ActionBarAPI {

    @Override
    public void sendBar(Player player, String message) {
        PACKET_CONTAINER.sendChatPacket(player, message, ChatMessageType.GAME_INFO);
    }

    @Override
    public void sendBar(Collection<? extends Player> players, String message) {
        for (Player player : players) {
            sendBar(player, message);
        }
    }
    @Deprecated
    @Override
    public void sendAnimatedBar(Player player, String message) {
        String last = (message += "                                         ")
                .charAt(0) == '§' ? "§" + message.charAt(1) : "";
        int length = message.length();
       //todo StringBuilder builder = new StringBuilder();//insert
        String[] chars = new String[length];
        for (int a = 0; a < length; ++a) {
            String msg;
            if (message.charAt(a) == '§') {
                continue;
            }
            if (a < 40) {
                msg = message.substring(0, a + 1);
            } else {
                int c = a - 40;
                msg = message.substring(c, a + 1);
                if (message.charAt(c) == '§') {
                    last = "§" + message.charAt(c + 1);
                    ++a;
                    msg = msg.substring(2);
                }
                msg = last + msg;
            }
            chars[a] = msg;
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
            for (String msg : chars) {
                if (msg == null) {
                    continue;
                }
                sendBar(player, msg);
            }
        }, 100, 100);
    }
}
