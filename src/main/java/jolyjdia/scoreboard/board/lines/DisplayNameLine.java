package jolyjdia.scoreboard.board.lines;

import jolyjdia.Main;
import jolyjdia.nms.interfaces.packet.PacketContainer;
import jolyjdia.nms.interfaces.packet.scoreboard.PacketScoreboardObjective;
import jolyjdia.nms.scoreboard.DObjective;
import jolyjdia.nms.scoreboard.ObjectiveActionMode;
import jolyjdia.nms.scoreboard.ObjectiveType;
import jolyjdia.utils.StringBind;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DisplayNameLine {
    private static final PacketContainer PACKET_CONTAINER = Main.PACKET_CONTAINER;

    private final DObjective dObjective;
    private final List<String> names = new ArrayList<>();
    private String name;
    private long speed;
    private boolean animation;
    private Player owner;
    private int d;
    private int t;

    public DisplayNameLine() {
        dObjective = new DObjective("RoflanBoard", "DisplayName", ObjectiveType.INTEGER);
        name = dObjective.getDisplayName();
        speed = -1;
        animation = false;
    }

    public void setName(String name) {
        this.name = name;
        this.dObjective.setDisplayName(name);
        this.names.clear();
        animation = false;
    }

    public void setNames(String name) {
        setName(name);
        animation = true;
        this.names.addAll(StringBind.getAnimation(name));
    }

    public void setNames(List<String> names, String name) {
        setName(name);
        if (names.size() > 1) {
            animation = true;
            this.names.addAll(names);
        }
    }

    public DObjective getdObjective() {
        return dObjective;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public List<String> getNames() {
        return names;
    }

    public boolean isAnimation() {
        return animation;
    }

    public Long getSpeed() {
        return speed;
    }

    public Runnable updateObjective() {
        return () -> {
            if (d == this.names.size()) {
                if (t == 120) {
                    t = 0;
                    d = 0;
                }
                this.dObjective.setDisplayName(" §8§l» §c§l" + name + "  ");
                this.t++;
            } else {
                this.dObjective.setDisplayName(names.get(d));
                ++this.d;
            }
            sendUpdatePacket();
        };
    }

    private void sendUpdatePacket() {
        if (owner == null) {
            return;
        }
        PacketScoreboardObjective packet = PACKET_CONTAINER.getScoreboardObjectivePacket(this.dObjective, ObjectiveActionMode.UPDATE);
        packet.sendPacket(owner);
    }

    public void setPlayerName(Player owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }
}
