package jolyjdia.utils;

import jolyjdia.Main;

public class InitPlugin {
    private final Main plugin;

    public InitPlugin(Main plugin) {
        this.plugin = plugin;
    }

    public final Main getPlugin() {
        return plugin;
    }

    public void onEnable() {}
    public void onDisable() {}
}
