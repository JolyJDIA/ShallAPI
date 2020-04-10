package jolyjdia.api.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public interface ConfigBuilder {
    void saveConfig();
    void saveDefaultConfig();
    void reloadConfig();
    FileConfiguration getConfig();
    Plugin getPlugin();
}
