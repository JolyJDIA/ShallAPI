package jolyjdia.hologram.lines;

import jolyjdia.api.hologram.lines.AnimationHoloLine;
import jolyjdia.hologram.CraftHologram;
import org.bukkit.Location;

import java.util.function.Supplier;

public class CraftAnimationLine extends CraftHoloLine implements AnimationHoloLine {

    private int currentStep;
    private Supplier<String> replacerLine;
    private int speed;

    private String text;

    public CraftAnimationLine(CraftHologram hologram, Location location, long speed, Supplier<String> replacerLine) {
        super(hologram, location);
        this.speed = speed <= 1 ? 1 : (int) speed;
        currentStep = 0;
        this.replacerLine = replacerLine;
    }

    @Override
    public void setReplacerLine(Supplier<String> supplier) {
        this.replacerLine = supplier;
    }

    public void setText(String text) {
        if (text == null || (text.equals(this.text)))
            return;

        this.text = text;

        customStand.setCustomName(text);
    }

    public boolean update() {
        if (currentStep == 0) {
            currentStep = this.speed;
            return true;
        } else {
            currentStep--;
            return false;
        }
    }

    public String getReplaceText() {
        return replacerLine.get();
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
