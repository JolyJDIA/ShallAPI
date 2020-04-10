package jolyjdia.api.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;

public class ConfigAccessor extends FileCustom implements ConfigBuilder {
    private final String fileName;
    private final Plugin plugin;
    private FileConfiguration cfg;

    public ConfigAccessor(@NotNull Plugin plugin, String fileName) {
        super(plugin, fileName);
        this.plugin = plugin;
        this.fileName = fileName;
        this.cfg = YamlConfiguration.loadConfiguration(getFile());
    }
    @Override
    public final void saveConfig() {
        if (cfg == null || getFile() == null) {
            return;
        }
        try {
            getConfig().save(getFile());
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not savePlayer config to " + getFile(), ex);
        }
    }
    @Override
    public final void saveDefaultConfig() {
        if (!getFile().exists()) {
            this.plugin.saveResource(fileName, false);

        }
    }
    @Override
    public final void reloadConfig() {
        this.cfg = YamlConfiguration.loadConfiguration(getFile());
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(plugin.getResource(fileName)), StandardCharsets.UTF_8)) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(reader);
            this.cfg.setDefaults(defConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public final FileConfiguration getConfig() {
        if (cfg == null) {
            this.reloadConfig();
        }
        return cfg;
    }

    @Override
    public final Plugin getPlugin() {
        return plugin;
    }
}