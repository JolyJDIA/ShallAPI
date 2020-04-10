package jolyjdia.gadgets.gadgets.microboss.microbes;

import jolyjdia.Main;
import jolyjdia.gadgets.gadgets.microboss.Minion;
import jolyjdia.utils.Head;
import jolyjdia.utils.MathUtils;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class DevilMinion extends Minion {
    private boolean move;
    private int time;
    private int globaltime;

    public DevilMinion(Player p) {
        super(p);
        this.minion.setCustomName("§cГАЧИ КЛАУН");
        this.minion.getEquipment().setHelmet(getRandomElfHead());
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta metaChestplate = (LeatherArmorMeta) chestplate.getItemMeta();
        metaChestplate.setColor(Color.RED);
        chestplate.setItemMeta(metaChestplate);
        this.minion.getEquipment().setChestplate(chestplate);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta metaLeggings = (LeatherArmorMeta) leggings.getItemMeta();
        metaLeggings.setColor(Color.RED);
        leggings.setItemMeta(metaLeggings);
        this.minion.getEquipment().setLeggings(leggings);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta metaBoots = (LeatherArmorMeta) boots.getItemMeta();
        metaBoots.setColor(Color.BLACK);
        boots.setItemMeta(metaBoots);
        this.minion.getEquipment().setBoots(boots);

        EulerAngle angle = new EulerAngle(0.5,0, 0);
        this.minion.setBodyPose(angle);
        this.minion.setVisible(false);
    }

    @Override
    public final void run() {
        mainPhysicsMotion();
        Location location = minion.getLocation();
        if (this.move) {
            this.spawnGift(location.add(0.0f, 0.5f, 0.0f));
            this.minion.teleport(location.add(randomRange(-0.5f,0.5f),randomRange(-0.5f,0.5f),randomRange(-0.5f,0.5f)));
            this.minion.setHeadPose(new EulerAngle(randomRange(-0.52f,0.52f),randomRange(-0.52f,0.52f),randomRange(-0.52f,0.52f)));
            getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 1);
            ++this.time;
            if (this.time >= 60) {
                this.move = false;
                this.time = 0;
                this.minion.setHeadPose(ZERO_ANGLE);
            }
        }
        if (globaltime % 10 == 0) {
            if (MathUtils.randomFloat() > 0.98f && !this.move) {
                this.minion.getWorld().playSound(location, Sound.ENTITY_CAT_HISS, 0.5F, 0.0F);
                this.move = true;
            }
            getWorld().spawnParticle(Particle.LAVA, location.subtract(0.0f, 0.1f, 0.0f), 1);
        }
        ++globaltime;
    }
    private final List<ArmorStand> gifts = new ArrayList<>();
    private static ItemStack getRandomGift() {
        Head var1 = null;
        switch(MathUtils.randomDouble(0, 12)) {
            case 0:
                var1 = Head.GOLD_PRESENT;
                break;
            case 1:
                var1 = Head.BLUE_PRESENT;
                break;
            case 2:
                var1 = Head.RED_PRESENT;
                break;
            case 3:
                var1 = Head.GREEN_PRESENT;
                break;
            case 4:
                var1 = Head.TURQUOISE_PRESENT;
                break;
            case 5:
                var1 = Head.LIGHT_BLUE_PRESENT;
                break;
            case 6:
                var1 = Head.AQUA_PRESENT;
                break;
            case 7:
                var1 = Head.BLACK_PRESENT;
                break;
            case 8:
                var1 = Head.DARK_ORANGE_PRESENT;
                break;
            case 9:
                var1 = Head.ORANGE_PRESENT;
                break;
            case 10:
                var1 = Head.DARK_PINK_PRESENT;
                break;
            case 11:
                var1 = Head.INDIGO_PRESENT;
                break;
            case 12:
                var1 = Head.PINK_PRESENT;
                break;
            case 13:
                var1 = Head.PURPLE_PRESENT;
        }

        assert var1 != null;
        return Head.getSkull(var1.getTexture());
    }

    private static ItemStack getRandomElfHead() {
        Head var1 = null;
        switch(MathUtils.randomDouble(0, 6)) {
            case 0:
                var1 = Head.ELFGIRL_1;
                break;
            case 1:
                var1 = Head.ELFGIRL_2;
                break;
            case 2:
                var1 = Head.ELFGIRL_3;
                break;
            case 3:
                var1 = Head.ELFMAN_1;
                break;
            case 4:
                var1 = Head.ELFMAN_2;
                break;
            case 5:
                var1 = Head.ELFMAN_3;
                break;
            case 6:
                var1 = Head.ELFMAN_4;
                break;
            case 7:
                var1 = Head.ELFMAN_5;
        }

        assert var1 != null;
        return Head.getSkull(var1.getTexture());
    }

    private void spawnGift(Location var1) {
        ArmorStand var2 = var1.getWorld().spawn(var1, ArmorStand.class);
        var2.setVisible(false);
        var2.setHeadPose(new EulerAngle(MathUtils.randomDouble(-0.17f, 0.17f), MathUtils.randomDouble(-0.52f, 0.52f), MathUtils.randomDouble(-0.52f, 0.52f)));
        var2.getEquipment().setHelmet(getRandomGift());
        var2.setSmall(true);
        this.gifts.add(var2);
        var2.setVelocity(new Vector(MathUtils.randomDouble(-0.2f, 0.2F), 0.2F, MathUtils.randomDouble(-0.2f, 0.2F)));
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), var2::remove, 10);
    }

    public static double randomRange(float v, float v1) {
        return (v + Math.random() * (v1 - v));
    }
}

