package jolyjdia.api.file;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileCustom {
    private final File file;

    public FileCustom(@NotNull File file) {
        this.file = file;
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }
    public FileCustom(String path) {
        this(new File(path));
    }

    public FileCustom(@NotNull Plugin plugin, String fileName) {
        this(new File(plugin.getDataFolder(), '\\' + fileName));
    }

    public final File getFile() {
        return file;
    }
}