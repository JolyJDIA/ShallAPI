package jolyjdia;

import jolyjdia.api.constant.GroupImpl;
import jolyjdia.api.constant.JoinMessage;
import jolyjdia.api.events.gamer.GamerJoinEvent;
import jolyjdia.api.events.gamer.GamerUpdateGroupEvent;
import jolyjdia.connector.packets.base.GamerBaseRequestPacket;
import jolyjdia.scoreboard.tag.CraftTag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class MainEvents implements Listener {
    private final Main main;

    public MainEvents(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(@NotNull PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player player = e.getPlayer();
        GamerBaseRequestPacket packet = new GamerBaseRequestPacket(player.getUniqueId());
        main.getChannel().writeAndFlush(packet);//todo: fix

        /*PermissionAttachment attachment = player.addAttachment(main);
        attachment.setPermission("bukkit.command.version", false);
        attachment.setPermission("bukkit.command.plugins", false);
        attachment.setPermission("minecraft.command.help", false);
        attachment.setPermission("bukkit.command.help", false);
        attachment.setPermission("minecraft.command.me", false);
        attachment.setPermission("bukkit.command.me", false);
        attachment.setPermission("minecraft.command.tell", false);
        attachment.setPermission("bukkit.command.tell", false);*/
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public static void onQuit(@NotNull PlayerQuitEvent e) {
        e.setQuitMessage(null);
        //SCORE_BOARD_API.removeDefaultTag(e.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public static void onGamerJoinEvent(@NotNull GamerJoinEvent e) {
        Player p = e.getGamer().getPlayer();
        GroupImpl group = e.getGamer().getGroup();
        if(group == GroupImpl.ADMIN) {
            p.setOp(true);
        }
        JoinMessage.joinMessage(e.getGamer());
        CraftTag playerTag = new CraftTag(group.getStar() + p.getName());
        playerTag.addPlayerToTeam(p);
        playerTag.setPrefix(group.getPrefix() + ' ');
        playerTag.disableCollidesForAll();
        playerTag.sendToAll();
       // SkinAPI.getSkinAsync(e.getGamer().getSkin()).thenAccept(e -> Main.NMS_API.setSkin(p, e));
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public static void onUpdateGroup(@NotNull GamerUpdateGroupEvent e) {
        Player p = e.getGamer().getPlayer();
        GroupImpl group = e.getGamer().getGroup();
        /*SCORE_BOARD_API.removeDefaultTag(p);
        @NonNls PlayerTag playerTag = SCORE_BOARD_API.createTag(
                group.getStar() + p.getName()//хуйня
        );
        playerTag.addPlayerToTeam(p);
        playerTag.setPrefix(group.getPrefix() + ' ');
        playerTag.disableCollidesForAll();
        SCORE_BOARD_API.setDefaultTag(p, playerTag);*/
    }
}
