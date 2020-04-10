package jolyjdia.nms.v1_15_R1.packet.scoreboard;

import jolyjdia.nms.interfaces.packet.scoreboard.PacketScoreBoardTeam;
import jolyjdia.nms.scoreboard.DTeam;
import jolyjdia.nms.scoreboard.TeamAction;
import jolyjdia.nms.util.ReflectionUtils;
import jolyjdia.nms.v1_15_R1.packet.DPacketBase;
import net.minecraft.server.v1_15_R1.ChatComponentText;
import net.minecraft.server.v1_15_R1.EnumChatFormat;
import net.minecraft.server.v1_15_R1.PacketPlayOutScoreboardTeam;

public class PacketScoreBoardTeamImpl extends DPacketBase<PacketPlayOutScoreboardTeam> implements PacketScoreBoardTeam {

    private DTeam team;
    private TeamAction teamAction;

    public PacketScoreBoardTeamImpl(DTeam team, TeamAction teamAction) {
        this.team = team;
        this.teamAction = teamAction;
    }

    @Override
    public void setTeam(DTeam team) {
        this.team = team;
        init();
    }

    @Override
    public void setTeamAction(TeamAction action) {
        this.teamAction = action;
        init();
    }

    @Override
    public TeamAction getTeamAction() {
        return teamAction;
    }

    @Override
    public DTeam getTeam() {
        return team;
    }

    @Override
    protected PacketPlayOutScoreboardTeam init() {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        ReflectionUtils.setFieldValue(packet, "e", team.getVisibility().getValue());
        ReflectionUtils.setFieldValue(packet, "f", team.getCollides().getValue());
        ReflectionUtils.setFieldValue(packet, "g", EnumChatFormat.RESET);
        ReflectionUtils.setFieldValue(packet, "a", team.getName());
        ReflectionUtils.setFieldValue(packet, "i", teamAction.getMode());

        ReflectionUtils.setFieldValue(packet, "h", team.getPlayers());

        if (teamAction == TeamAction.CREATE || teamAction == TeamAction.UPDATE) {
            ReflectionUtils.setFieldValue(packet, "b", new ChatComponentText(team.getDisplayName()));
            ReflectionUtils.setFieldValue(packet, "j", team.packOptionData());
            ReflectionUtils.setFieldValue(packet, "c", new ChatComponentText(team.getPrefix()));
            ReflectionUtils.setFieldValue(packet, "d", new ChatComponentText(team.getSuffix()));
        }

        return packet;
    }
}
