package jolyjdia.hologram;

import jolyjdia.Main;
import jolyjdia.api.hologram.Hologram;
import jolyjdia.api.hologram.HologramAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class HologramAPIImpl implements HologramAPI {


    private final HologramManager hologramManager;

    public HologramAPIImpl() {
        this.hologramManager = new HologramManager();
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new HologramTask(hologramManager), 0, 1L);
        //ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //executorService.scheduleAtFixedRate(new HologramTask(hologramManager), 0, 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public Hologram createHologram(Location location) {
        return new CraftHologram(hologramManager, location);
    }

    @Override
    public List<Hologram> getHolograms() {
        return new ArrayList<>(hologramManager.getHolograms());
    }
}
