package jolyjdia.hologram;

import jolyjdia.hologram.lines.CraftAnimationLine;
import jolyjdia.hologram.lines.CraftHoloLine;
import jolyjdia.hologram.lines.CraftItemDropLine;
import jolyjdia.hologram.lines.CraftItemFloatingLine;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HologramTask implements Runnable {

    private final HologramManager hologramManager;

    HologramTask(HologramManager hologramManager) {
        this.hologramManager = hologramManager;
    }

    @Override
    public void run() {
        for (CraftHologram hologram : hologramManager.getHolograms()) {
            for (CraftHoloLine craftHoloLine : hologram.getLines()){

                if (craftHoloLine instanceof CraftItemFloatingLine){
                    if (!(((CraftItemFloatingLine) craftHoloLine).isRotate()))
                        continue;
                    ((CraftItemFloatingLine) craftHoloLine).update();
                }

                if (craftHoloLine instanceof CraftItemDropLine) {
                    CraftItemDropLine line = (CraftItemDropLine) craftHoloLine;

                    if (line.isPickup()) {
                        for (Player player : Bukkit.getOnlinePlayers())
                            line.checkPickup(player, hologram);
                    }
                }

                if (craftHoloLine instanceof CraftAnimationLine) {
                    CraftAnimationLine animationLine = ((CraftAnimationLine) craftHoloLine);
                    if (animationLine.update()) {

                        String replaceText = animationLine.getReplaceText();
                        if (replaceText == null)
                            continue;

                        animationLine.setText(replaceText);
                    }
                }
            }
        }
    }
}
