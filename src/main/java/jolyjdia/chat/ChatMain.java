package jolyjdia.chat;

import jolyjdia.Main;
import jolyjdia.utils.InitPlugin;
import org.bukkit.Bukkit;

public class ChatMain extends InitPlugin {
    public ChatMain(Main plugin) {
        super(plugin);
    }

    @Override
    public final void onEnable() {
        Bukkit.getPluginManager().registerEvents(new ChatListener(), getPlugin());
    }
}
