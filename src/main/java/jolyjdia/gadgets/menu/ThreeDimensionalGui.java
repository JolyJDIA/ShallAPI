package jolyjdia.gadgets.menu;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ThreeDimensionalGui {
    private final Player player;
    private final Location center;
    private final int currentPage = -1;
    private static final Button previous = new Button("§6previous", Material.BEDROCK, () -> {});
    private static final Button next = new Button("§6next", Material.BEDROCK, () -> {});
    public static final int MAX_ELEMENTS_PAGE = 6;
    private static final int MAX_PAGE = 3;
    private static final List<Category> buttons = List.of(
            new Category(
                    new Button("BRUH1", Material.SLIME_BLOCK, () -> {}),
                    List.of(
                            new Button("BRUH1", Material.SLIME_BLOCK, () -> {}),
                            new Button("BRUH2", Material.SLIME_BLOCK, () -> {}),
                            new Button("BRUH3", Material.SLIME_BLOCK, () -> {})
                    )
            ),
            new Category(
                    new Button("BRUH1", Material.SLIME_BLOCK, () -> {}),
                    List.of(
                            new Button("GAY1", Material.REDSTONE_BLOCK, () -> {}),
                            new Button("GAY2", Material.GOLD_BLOCK, () -> {}),
                            new Button("GAY3", Material.DIAMOND_BLOCK, () -> {})
                    )
            ),
            new Category(
                    new Button("BRUH1", Material.SLIME_BLOCK, () -> {}),
                    List.of(
                            new Button("BRUH1", Material.SLIME_BLOCK, () -> {}),
                            new Button("BRUH2", Material.SLIME_BLOCK, () -> {}),
                            new Button("BRUH3", Material.SLIME_BLOCK, () -> {})
                    )
            ),
            new Category(
                    new Button("BRUH1", Material.SLIME_BLOCK, () -> {}),
                    List.of(
                            new Button("GAY1", Material.REDSTONE_BLOCK, () -> {}),
                            new Button("GAY2", Material.GOLD_BLOCK, () -> {}),
                            new Button("GAY3", Material.DIAMOND_BLOCK, () -> {})
                    )
            ),
            new Category(
                    new Button("BRUH1", Material.SLIME_BLOCK, () -> {}),
                    List.of(
                            new Button("BRUH1", Material.SLIME_BLOCK, () -> {}),
                            new Button("BRUH2", Material.SLIME_BLOCK, () -> {}),
                            new Button("BRUH3", Material.SLIME_BLOCK, () -> {})
                    )
            ),
            new Category(
                    new Button("BRUH1", Material.SLIME_BLOCK, () -> {}),
                    List.of(
                            new Button("GAY1", Material.REDSTONE_BLOCK, () -> {}),
                            new Button("GAY2", Material.GOLD_BLOCK, () -> {}),
                            new Button("GAY3", Material.DIAMOND_BLOCK, () -> {})
                    )
            )
    );
    List<ArmorStand> list = new ArrayList<>();

    public ThreeDimensionalGui(Player player) {
        this.player = player;
        this.center = player.getLocation().add(0, 0.5, 0);
    }
    public void setupItem() {
        /*for(int i = 0; i < MAX_ELEMENTS_PAGE; ++i) {
            Button head = buttons.getSender(i).getHead();
            list.add(ofButton(center, head.material, head.text));
        }*/
        int amount = 7;
        double increment = Math.PI / amount;
        double yaw = Math.toRadians(center.getYaw());//если джава 13 и выше, в противном случае лучше юзать MathUtils.toRadians
        for (int i = 1; i < amount; ++i) {
            double angel = i * increment + yaw;
            double x = 3.5 * Math.cos(angel);
            double z = 3.5 * Math.sin(angel);
            center.add(x, 0, z);
            Button head = buttons.get(i-1).getHead();
            ofButton(center, head.material, head.text).teleport(center);
            center.subtract(x, 0, z);
        }
    }
    public void transitionButtons() {
        if(currentPage < buttons.size()) {

        }
    }

    public static ArmorStand ofButton(Location layout, Material material, String text) {
        if(layout.getWorld() == null) {
            throw new NullPointerException("this world does not exist to position the button");
        }
        ArmorStand field = layout.getWorld().spawn(layout, ArmorStand.class);
        field.setSmall(true);
        field.setGravity(false);
        field.setVisible(false);
        field.setCustomNameVisible(true);
        field.setCustomName(text);
        EntityEquipment equipment = field.getEquipment();
        if(equipment != null) {
            equipment.setHelmet(new ItemStack(material));
        }
        return field;
    }
    private static class Button {
        private int heapIndex;
        private final String text;
        private final Material material;
        private final Runnable runnable;

        public Button(String text, Material material, Runnable runnable) {
            this.text = text;
            this.material = material;
            this.runnable = runnable;
        }

        public Runnable getRunnable() {
            return runnable;
        }

        public int getHeapIndex() {
            return heapIndex;
        }

        public void setHeapIndex(int heapIndex) {
            this.heapIndex = heapIndex;
        }

        public Material getMaterial() {
            return material;
        }

        public String getText() {
            return text;
        }
    }
    private static class Category {
        private final Button head;
        private final List<Button> buttons;

        public Category(Button head, List<Button> buttons) {
            this.head = head;
            this.buttons = buttons;
        }

        public Button getHead() {
            return head;
        }

        public List<Button> getButtons() {
            return buttons;
        }
    }
}
