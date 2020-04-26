package jolyjdia.scoreboard.sidebar;

import jolyjdia.scoreboard.wrapper.WrapperPlayServerScoreboardDisplayObjective;
import jolyjdia.scoreboard.wrapper.WrapperPlayServerScoreboardObjective;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

class SidebarObjective {
    private static final int SIDEBAR = 1;

    private final String name;
    private String displayName;
    
    SidebarObjective(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }


    void setDisplayName(String displayName, @NotNull Sidebar sidebar) {
        this.displayName = displayName;

        WrapperPlayServerScoreboardObjective packet = getPacket();
        packet.setMode(WrapperPlayServerScoreboardObjective.Mode.UPDATE_VALUE);
        sidebar.broadcastPacket(packet);
    }

    void create(Player player) {
        WrapperPlayServerScoreboardObjective packet = getPacket();
        packet.setMode(WrapperPlayServerScoreboardObjective.Mode.ADD_OBJECTIVE);

        packet.sendPacket(player);
    }

    void remove(Player player) {
        WrapperPlayServerScoreboardObjective packet = getPacket();
        packet.setMode(WrapperPlayServerScoreboardObjective.Mode.REMOVE_OBJECTIVE);

        packet.sendPacket(player);
    }

    void show(Player player) {
        WrapperPlayServerScoreboardDisplayObjective displayObjective = new WrapperPlayServerScoreboardDisplayObjective();
        displayObjective.setPosition(SIDEBAR);
        displayObjective.setScoreName(name);
        displayObjective.sendPacket(player);
    }

    private @NotNull WrapperPlayServerScoreboardObjective getPacket() {
        WrapperPlayServerScoreboardObjective packet = new WrapperPlayServerScoreboardObjective();
        packet.setDisplayName(displayName);
        packet.setName(name);
        packet.setHealthDisplay(WrapperPlayServerScoreboardObjective.HealthDisplay.INTEGER);
        return packet;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
}