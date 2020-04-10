package jolyjdia.gadgets.gadgets;

import org.bukkit.Material;

public class RadicalGadgets {
    private final Class<? extends Gadget> aClass;
    private final Material badge;
    private final String title;

    RadicalGadgets(Material badge, String title, Class<? extends Gadget> aClass) {
        this.badge = badge;
        this.title = title;
        this.aClass = aClass;
    }

    public Class<? extends Gadget> getGadgetClass() {
        return aClass;
    }

    public Material getBadge() {
        return badge;
    }

    public String getTitle() {
        return title;
    }
}
