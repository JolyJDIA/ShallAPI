package jolyjdia.chat;

import com.google.common.collect.Maps;
import jolyjdia.api.AccountAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.IdentityHashMap;
import java.util.UUID;

public class ChatListener implements Listener {
    private final IdentityHashMap<UUID, Long> cooldown = Maps.newIdentityHashMap();

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public final void onAsyncChat(@NotNull AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        AccountAPI.getIfLoaded(uuid).ifPresent(gamer -> {
            if (gamer.getGroup().getLevel() < 3) {
                if(cooldown.containsKey(uuid)) {
                    @NonNls long millis = cooldown.get(uuid) / 1000L + 2 - System.currentTimeMillis() / 1000L;
                    if (millis <= 0L) {
                        cooldown.remove(uuid);
                    } else {
                        player.sendMessage("Не флудите, подождитие еще §4§l" + millis + "§f секунд");
                        return;
                    }
                } else {
                    cooldown.put(uuid, System.currentTimeMillis());
                }
            }
            String suffix = gamer.getGroup().getSuffix();
            String prefix = gamer.getPrefix();
            String msg = e.getMessage();
            String format = ' ' + prefix + ' ' + gamer.getPlayer().getName() + "§f: " + msg;
            for(Player p : gamer.getPlayer().getWorld().getPlayers()) {
                p.sendMessage(format);
            }
        });
    }
}
