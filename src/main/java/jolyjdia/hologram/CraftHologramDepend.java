package jolyjdia.hologram;

import jolyjdia.api.hologram.lines.AnimationHoloLine;
import jolyjdia.api.hologram.lines.ItemDropLine;
import jolyjdia.api.hologram.lines.ItemFloatingLine;
import jolyjdia.api.hologram.lines.TextHoloLine;
import jolyjdia.hologram.lines.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class CraftHologramDepend {
    private final CraftHologram hologram;

    final Set<Player> playersVision = new HashSet<>();

    CraftHologramDepend(CraftHologram craftHologram) {
        hologram = craftHologram;
    }

    TextHoloLine setTextLine(int index, String text) {
        CraftTextHoloLine textLine = null;
        if ((index >= 0 && index <= hologram.getLines().size())) {
            double space = getSpace(hologram.getLines().size());
            textLine = new CraftTextHoloLine(hologram, hologram.getLocation().subtract(0, space, 0), text);
            hologram.getLines().add(index, textLine);
        }
        updateVision();
        return textLine;
    }

    AnimationHoloLine setAnimationLine(int index, long speed, Supplier<String> replacerLine) {
        CraftAnimationLine animationLine = null;
        if ((index >= 0 && index <= hologram.getLines().size())) {
            double space = getSpace(hologram.getLines().size());
            animationLine = new CraftAnimationLine(hologram,
                    hologram.getLocation().subtract(0, space, 0), speed, replacerLine);
            hologram.getLines().add(index, animationLine);
        }
        updateVision();
        return animationLine;
    }

    ItemDropLine setItemDropLine(int index, boolean pickup, Material item) {
        CraftItemDropLine itemDropLine = null;
        if ((index >= 0 && index <= hologram.getLines().size())) {
            double space = getSpace(hologram.getLines().size());
            itemDropLine = new CraftItemDropLine(hologram, hologram.getLocation().subtract(0, space + 0.65, 0), pickup, item);
            hologram.getLines().add(index, itemDropLine);
        }
        updateVision();
        return itemDropLine;
    }

    ItemFloatingLine setItemFloatingLine(boolean rotate, int index, Material item) {
        CraftItemFloatingLine itemFloatingLine = null;
        if ((index >= 0 && index <= hologram.getLines().size())) {
            double space = getSpace(hologram.getLines().size());
            Location location = hologram.getLocation().subtract(0, space
                    + 1 - CraftItemFloatingLine.getItemFloatingEnter(item), 0);
            itemFloatingLine = new CraftItemFloatingLine(hologram, rotate, location, item);
            hologram.getLines().add(index, itemFloatingLine);
        }
        updateVision();
        return itemFloatingLine;
    }

    private double getSpace(int index) {
        double space = 0;
        for (int i = 0; i < index; i++) {
            CraftHoloLine craftHoloLine = hologram.getLines().get(i);

            if (craftHoloLine instanceof CraftTextHoloLine || craftHoloLine instanceof CraftAnimationLine)
                space += 0.3;
            else if (craftHoloLine instanceof CraftItemDropLine)
                space += 0.7;
            else if (craftHoloLine instanceof CraftItemFloatingLine)
                space += 1.05;

        }
        return space;
    }

    void updateLines() {
        for (int i = 0; i < hologram.getLines().size(); i++) {
            CraftHoloLine craftHoloLine = hologram.getLines().get(i);
            if (craftHoloLine == null)
                continue;

            double enter = 0;

            if (craftHoloLine instanceof CraftItemDropLine) {
                enter = 0.65;
            } else if (craftHoloLine instanceof CraftItemFloatingLine) {
                enter = 1 - CraftItemFloatingLine.getItemFloatingEnter(((ItemFloatingLine) craftHoloLine).getItem());
            }

            craftHoloLine.teleport(hologram.getLocation().clone().subtract(0, getSpace(i) + enter, 0));
        }
    }

    private void updateVision() {
        if (hologram.isPublic()) {
            hologram.setPublic(true);
            return;
        }

        playersVision.forEach(player -> {
            if (player != null && player.isOnline()) {
                for (CraftHoloLine craftHoloLine : hologram.getLines()) {
                    craftHoloLine.showTo(player);
                }
            }
        });
    }
}
