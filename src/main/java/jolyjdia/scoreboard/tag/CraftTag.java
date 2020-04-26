package jolyjdia.scoreboard.tag;

import jolyjdia.scoreboard.wrapper.AbstractPacket;
import jolyjdia.scoreboard.wrapper.WrapperPlayServerScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CraftTag {
    private final WrapperPlayServerScoreboardTeam team = new WrapperPlayServerScoreboardTeam();
    private boolean enabled;

    public CraftTag(String name) {
        team.setName(name);
    }
    
    public void sendToAll() {
        enabled = true;
        Bukkit.getOnlinePlayers().forEach(p -> {
            team.setMode(WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED);
            team.sendPacket(p);

           // team.setMode(WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED);
            //team.sendPacket(p);

            team.setMode(WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED);
            team.sendPacket(p);
        });
    }
    
    public void sendTo(Collection<? extends Player> players) {
        enabled = true;
        WrapperPlayServerScoreboardTeam p1 = new WrapperPlayServerScoreboardTeam();
        p1.setMode(WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED);

        WrapperPlayServerScoreboardTeam p2 = new WrapperPlayServerScoreboardTeam();
        p1.setMode(WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED);

        WrapperPlayServerScoreboardTeam p3 = new WrapperPlayServerScoreboardTeam();
        p1.setMode(WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED);
        players.forEach(p -> {
            p1.sendPacket(p);
            p2.sendPacket(p);
            p3.sendPacket(p);
        });
    }
    
    public void sendTo(Player player) {
        enabled = true;
        team.setMode(WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED);
        team.sendPacket(player);

        team.setMode(WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED);
        team.sendPacket(player);

        team.setMode(WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED);
        team.sendPacket(player);
    }
    
    public void addPlayerToTeam(Player player) {
        team.setMode(WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED);
        team.getPlayers().add(player.getName());
        team.sendPacket(player);
    }
    
    public void addPlayersToTeam(Collection<? extends Player> players) {
        team.setMode(WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED);
        players.forEach(team::sendPacket);
    }
    
    public void addNamesToTeam(Collection<String> names) {
        team.setMode(WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED);
    }
    
    public void removePlayerFromTeam(Player player) {
        team.setMode(WrapperPlayServerScoreboardTeam.Mode.PLAYERS_REMOVED);
        team.sendPacket(player);
    }
    
    public boolean isContainsTeam(Player player) {
        String name = player.getName();
        return team.getPlayers().contains(name);
    }

    
    public boolean isEmpty() {
        return team.getPlayers().isEmpty();
    }
    
    public List<String> getPlayersTeam() {
        return new ArrayList<>(team.getPlayers());
    }
    
    public void setFriendInv(boolean friendInv) {
       /* team.setSetFriendInv(friendInv);

        if (enabled) {
            sendPacket(CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE));
        }*/
    }

    public void setPrefixSuffix(String prefix, String suffix) {
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setMode(WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED);
        Bukkit.getOnlinePlayers().forEach(team::sendPacket);
    }

    public void setPrefix(String prefix) {
        team.setPrefix(prefix);
        team.setMode(WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED);
        Bukkit.getOnlinePlayers().forEach(team::sendPacket);
    }

    public void setSuffix(String suffix) {
        team.setSuffix(suffix);
        team.setMode(WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED);
        Bukkit.getOnlinePlayers().forEach(team::sendPacket);
    }

    public void disableCollidesForAll() {
        setCollides("never");
    }

    public void setCollides(String collides) {
        team.setCollisionRule(collides);
        team.setMode(WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED);
        Bukkit.getOnlinePlayers().forEach(team::sendPacket);
    }

    public void remove() {
        team.setMode(WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED);
        Bukkit.getOnlinePlayers().forEach(team::sendPacket);

        team.getPlayers().clear();
        enabled = false;
    }
}
