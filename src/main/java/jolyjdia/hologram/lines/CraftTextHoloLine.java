package jolyjdia.hologram.lines;

import jolyjdia.api.hologram.lines.TextHoloLine;
import jolyjdia.hologram.CraftHologram;
import org.bukkit.Location;

public class CraftTextHoloLine extends CraftHoloLine implements TextHoloLine {

    private String text;

    public CraftTextHoloLine(CraftHologram hologram, Location location, String text) {
        super(hologram, location);
        this.text = text;
        customStand.setCustomName(text);
    }

    @Override
    public void setText(String text) {
        this.text = text;
        customStand.setCustomName(text);
    }

    @Override
    public String getText() {
        return text;
    }
}
