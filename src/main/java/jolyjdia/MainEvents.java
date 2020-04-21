package jolyjdia;

import jolyjdia.api.boards.PlayerTag;
import jolyjdia.api.constant.GroupImpl;
import jolyjdia.api.events.gamer.GamerJoinEvent;
import jolyjdia.api.events.gamer.GamerUpdateGroupEvent;
import jolyjdia.api.player.GamePlayer;
import jolyjdia.api.skin.SkinAPI;
import jolyjdia.connector.packets.ClientGetBaseDataPacket;
import jolyjdia.utils.BukkitUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import static jolyjdia.Main.SCORE_BOARD_API;

public class MainEvents implements Listener {

    private final Main main;
    public MainEvents(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(@NotNull PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player player = e.getPlayer();
        ClientGetBaseDataPacket packet = new ClientGetBaseDataPacket(player.getUniqueId());
        packet.sendPacket(main.getChannel());

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

    @EventHandler
    public static void onQuit(@NotNull PlayerQuitEvent e) {
        e.setQuitMessage(null);
        SCORE_BOARD_API.removeDefaultTag(e.getPlayer());
    }
    @EventHandler
    public static void onGamerJoinEvent(GamerJoinEvent e) {
        Player p = e.getGamer().getPlayer();
        GroupImpl group = e.getGamer().getGroup();
        if(group == GroupImpl.ADMIN) {
            p.setOp(true);
        }
        BukkitUtils.messagesPlayers(' ' + group.getPrefix() + ' ' + p.getName() + " §fзашел на сервер");
        @NonNls PlayerTag playerTag = SCORE_BOARD_API.createTag(group.getStar() + p.getName());
        playerTag.addPlayerToTeam(p);
        playerTag.setPrefix(group.getPrefix() + ' ');
        playerTag.disableCollidesForAll();
        SCORE_BOARD_API.setDefaultTag(p, playerTag);
       // SkinAPI.getSkinAsync(e.getGamer().getSkin()).thenAccept(e -> Main.NMS_API.setSkin(p, e));
    }
    @EventHandler
    public static void onUpdateGroup(GamerUpdateGroupEvent e) {
        Player p = e.getGamer().getPlayer();
        GroupImpl group = e.getGamer().getGroup();
        SCORE_BOARD_API.removeDefaultTag(p);
        @NonNls PlayerTag playerTag = SCORE_BOARD_API.createTag(
                group.getStar() + p.getName()//хуйня
        );
        playerTag.addPlayerToTeam(p);
        playerTag.setPrefix(group.getPrefix() + ' ');
        playerTag.disableCollidesForAll();
        SCORE_BOARD_API.setDefaultTag(p, playerTag);
    }
}
