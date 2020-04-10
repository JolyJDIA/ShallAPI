package jolyjdia.api.hologram;

import jolyjdia.api.craft.PacketObject;
import jolyjdia.api.hologram.lines.AnimationHoloLine;
import jolyjdia.api.hologram.lines.ItemDropLine;
import jolyjdia.api.hologram.lines.ItemFloatingLine;
import jolyjdia.api.hologram.lines.TextHoloLine;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public interface Hologram extends PacketObject {

    AnimationHoloLine addAnimationLine(long speed, Supplier<String> replacerLine);
    TextHoloLine addTextLine(String text);
    ItemDropLine addDropLine(boolean pickup, Material item);
    ItemFloatingLine addBigItemLine(boolean rotate, Material item);

    TextHoloLine insertTextLine(int index, String text);
    ItemDropLine insertDropLine(int index, boolean pickup, Material item);
    ItemFloatingLine insertBigItemLine(int index, boolean rotate, Material item);

    List<HoloLine> getHoloLines();
    <T extends HoloLine> T getHoloLine(int index);

    void addTextLine(Collection<String> listText);

    int getSize();

    void removeLine(int index);
    void removeLine(HoloLine line);

    /**
     * Получить локацию энтити
     * @return - локация энтити
     */
    Location getLocation();

    /**
     * телепортировать энтити куда-то
     * @param location - куда телепортировать
     */
    void onTeleport(Location location);
}
