package jolyjdia.api.bungee;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class BungeeMessageListener implements PluginMessageListener {
    private final JavaPlugin javaPlugin;

    public BungeeMessageListener(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        Bukkit.getMessenger().registerIncomingPluginChannel(javaPlugin, "RoflanSkins", this);

        initOutgoingChannel("RoflanGroup");
        //initOutgoingChannel("LastPrefix");
        initOutgoingChannel("RoflanSettings");
    }

    @Override
    public void onPluginMessageReceived(@NotNull String s, @NotNull Player player, @NotNull byte[] bytes) {

    }

    private void initOutgoingChannel(String name) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(javaPlugin, name);
    }
}
