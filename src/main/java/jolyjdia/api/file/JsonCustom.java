package jolyjdia.api.file;

import com.google.gson.Gson;
import jolyjdia.utils.StringBind;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class JsonCustom extends FileCustom {

    public JsonCustom(@NotNull Plugin plugin, String fileName) {
        super(plugin, fileName);
        if (getFile().length() == 0) {
            this.create();
        }
    }

    @Nullable
    public final <T> T load(Type type) {
        try (FileInputStream fileInputStream = new FileInputStream(getFile());
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8)
        ) {
            return StringBind.fromJson(inputStreamReader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public final void create() {
        try (PrintWriter pw = new PrintWriter(getFile(), StandardCharsets.UTF_8)) {
            pw.print("{}");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public final void save(Object object) {
        try (PrintWriter pw = new PrintWriter(getFile(), StandardCharsets.UTF_8)) {
            pw.print(StringBind.toJson(object));
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}