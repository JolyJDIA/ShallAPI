package jolyjdia.api.command;

import jolyjdia.Main;
import jolyjdia.api.AccountAPI;
import jolyjdia.api.permission.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class GroupCommands implements CommandExecutor {
    private final Main main;
    @NonNls private static final String HELP =
            "§f/group set <игрока> <группа> §7- обновить привилегию\n" +
            "§f/group migrate <старый ник> <новый ник> §7- перенос привилегии\n" +
            "§f/group clear <ник> §7- снятие привилегии\n" +
            "§f/group list §7- список групп\n" +
            "§f/group info <ник> §7- информация о игроке";

    public GroupCommands(Main main) {
        this.main = main;
    }

    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            if(cmd.getName().equalsIgnoreCase("group")) {
                sender.sendMessage(HELP);
                return false;
            }
        } else if(args.length == 3) {
            if (args[0].equalsIgnoreCase("set")) {
                if (PermissionManager.notExistGroup(args[2])) {
                    sender.sendMessage("§6Сервер: §fДанной группы не существует");
                    return false;
                }
                // OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                Player p = Bukkit.getPlayer(args[1]);
                assert p != null;
                AccountAPI.getIfLoaded(p.getUniqueId()).ifPresentOrElse(e -> {
                    e.setGroup(PermissionManager.getGroupByName(args[2]));
                    sender.sendMessage("§6Сервер: §fПривилегия установлена");
                }, () -> sender.sendMessage("§6Сервер: §fДанного игрока нет на сервере"));
            }
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("clear")) {
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                AccountAPI.getIfLoaded(p.getUniqueId()).ifPresentOrElse(e -> {
                    e.setGroup(PermissionManager.getDefaultGroup());
                    sender.sendMessage("§6Сервер: §fПривилегия обновлена на §adefault");
                }, () -> sender.sendMessage("§6Сервер: §fДанного игрока нет на сервере"));
            } else if(args[0].equalsIgnoreCase("info")) {
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                AccountAPI.getIfLoaded(p.getUniqueId()).ifPresentOrElse(e -> {
                    sender.sendMessage(e.getGroup().getName());
                }, () -> sender.sendMessage("§cДанный игрок еще не заходил на сервер"));
            }
        } else if(args.length == 1) {
            if(args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(String.valueOf(PermissionManager.groupNameSet()));
            }
        }
        return false;
    }
}
