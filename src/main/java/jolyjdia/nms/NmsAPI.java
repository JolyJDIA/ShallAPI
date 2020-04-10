package jolyjdia.nms;

import jolyjdia.nms.interfaces.NmsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class NmsAPI {

    private NmsManager manager;

    public void init(JavaPlugin plugin) {
        if (manager != null) {
            return;
        }

        try {
            manager = (NmsManager) Class.forName("net.minecraft.server.v1_15_R1.NmsManager_v1_15_R1")
                    .getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getServer().getConsoleSender().sendMessage("§4NMS API not found, Plugin DartaAPI disabled (");
            Bukkit.getPluginManager().disablePlugin(plugin);
            Bukkit.setWhitelist(true);
        }
    }
}
