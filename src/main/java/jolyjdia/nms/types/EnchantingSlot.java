package jolyjdia.nms.types;

public enum EnchantingSlot {
    TO_ENCHANT(0),
    LAPIS(1);

    private final int slot;

    EnchantingSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
