package jolyjdia.gadgets.menu.item;

public class InteractableItem {
    private InteractClickable interactable;
    private Item3D item;

    public InteractableItem(Item3D item, InteractClickable click) {
        this.item = item;
        this.interactable = click;
    }

    public final Item3D getItem() {
        return this.item;
    }

    public final void setItem(Item3D item) {
        this.item = item;
    }

    public final InteractClickable getInteractable() {
        return this.interactable;
    }

    public void setInteractable(InteractClickable click) {
        this.interactable = click;
    }
}
