package jolyjdia.clear;

import jolyjdia.Main;
import jolyjdia.utils.BukkitUtils;
import jolyjdia.utils.InitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

public class ClearLag extends InitPlugin {
    public ClearLag(Main plugin) {
        super(plugin);
    }

    @Override
    public final void onEnable() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), () -> {
            long count = Bukkit.getWorlds().stream().mapToLong(e -> e.getEntities().stream().filter(t -> {
                if (t.getType() == EntityType.DROPPED_ITEM) {
                    t.remove();
                    return true;
                }
                return false;
            }).count()).sum();
            BukkitUtils.messagesPlayers("§bОчистка: §7Удалено предметов с пола: §c§l" + count);
        }, 0L, 5000);
    }
}