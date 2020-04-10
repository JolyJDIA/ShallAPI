package jolyjdia.info;

import jolyjdia.Main;
import jolyjdia.api.file.ConfigAccessor;
import jolyjdia.utils.BukkitUtils;
import jolyjdia.utils.InitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AutoMessage extends InitPlugin implements Listener {
    private String prefix;
    private List<String> msgs;
    private final List<String> help = new ArrayList<>();
    private int index;
    private ConfigAccessor config;

    public AutoMessage(Main plugin) {
        super(plugin);
    }

    @Override
    public final void onEnable() {
        this.config = new ConfigAccessor(getPlugin(), "messages.yml");
        List<String> helps = config.getConfig().getStringList("help");
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < helps.size(); ++i) {
            builder.append(helps.get(i)).append('\n');
            if((i > 9 && i % 10 == 0) || i == helps.size()-1) {
                help.add(builder.toString());
                builder.setLength(0);
            }
        }
        this.prefix = config.getConfig().getString("prefix");
        this.msgs = config.getConfig().getStringList("messages");
        Bukkit.getPluginManager().registerEvents(this, getPlugin());
        Objects.requireNonNull(Bukkit.getPluginCommand("helps")).setExecutor(new HelpCommand(this));
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), () -> {
            index = index == msgs.size() ? 0 : index;
            BukkitUtils.messagesPlayers(prefix + msgs.get(index));
            ++index;
        }, 500L, 1200);
    }


    public final ConfigAccessor getConfig() {
        return config;
    }

    public final List<String> getHelp() {
        return help;
    }

    @EventHandler
    public final void onMoth(@NotNull ServerListPingEvent e) {
        e.setMotd(Objects.requireNonNull(getPlugin().getConfig().getString("motd")));
    }
}