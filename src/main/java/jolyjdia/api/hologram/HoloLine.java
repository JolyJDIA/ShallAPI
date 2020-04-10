package jolyjdia.api.hologram;

import jolyjdia.api.craftentity.stand.CustomStand;

public interface HoloLine {
    CustomStand getCustomStand();

    /**
     * удалить строчку
     */
    void delete();
}
