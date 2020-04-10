package jolyjdia.gadgets.gadgets.microboss;

import jolyjdia.gadgets.gadgets.Gadget;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class Minion extends Gadget {
    public static final float RISE = 0.008F;
    public static final EulerAngle ZERO_ANGLE = new EulerAngle(0, 0, 0);
    private boolean loop;
    private float step;
    public final ArmorStand minion;

    protected Minion(Player player) {
        super(player);
        //в старт
        this.minion = player.getWorld().spawn(getLocation(), ArmorStand.class);
        this.minion.setSmall(true);
        this.minion.setGravity(false);
        this.minion.setArms(true);
        this.minion.setBasePlate(false);
        this.minion.setMarker(true);
        this.minion.setCustomNameVisible(true);
    }

    @Override
    public void run() {}

    @Override
    public void start() {}

    @Override
    public final void stop() {
        this.minion.remove();
    }

    protected final void mainPhysicsMotion() {
        Location loc = getLocation();
       // if(!getPlayer().isSprinting()) {
         //   this.loop = loop ? (((step -= RISE)) >= 0.0f) : (((step += RISE)) >= 0.2f);
        //    loc.setY(loc.getY()+step);
       // }
        this.minion.teleport(loc);
    }

    @Override
    public final @NotNull Location getLocation() {
        Location loc = getPlayer().getLocation();
        loc.setPitch(0.0F);
        loc.setYaw(loc.getYaw() + 50.0F);
        loc.add(loc.getDirection().normalize().multiply(-1.2f)).add(0, 1.2, 0);
        Location playerLocation = getPlayer().getLocation();
        loc.setYaw(playerLocation.getYaw());
        loc.setPitch(playerLocation.getPitch());
        return loc;
    }
}
