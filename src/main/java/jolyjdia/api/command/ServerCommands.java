package jolyjdia.api.command;

import jolyjdia.api.AccountAPI;
import jolyjdia.api.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@Deprecated
public class ServerCommands implements CommandExecutor {

    @Override
    public final boolean onCommand(@NonNls @NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length == 2) {
            if (cmd.getName().equalsIgnoreCase("addmoney")) {
                int keys;
                try {
                    keys = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§6Сервер: §cНекорректный формат числа");
                    return false;
                }
                @NonNls Player player = Bukkit.getPlayer(args[0]);
                UUID uuid;
                if (player == null || !player.isOnline()) {
                    uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
                } else {
                    uuid = player.getUniqueId();
                    player.sendMessage("§6Сервер: §fВам было начислено: §e" + keys + "§f монет");
                }
                Optional<GamePlayer> optional = AccountAPI.getIfLoaded(uuid);
                if (optional.isPresent()) {
                    optional.get().addMoney(keys);
                    sender.sendMessage("§6Сервер: §fВы начислили игроку монет");
                } else {
                    sender.sendMessage("§cДанный игрок еще не заходил на сервер");
                }
            }
            if (cmd.getName().equalsIgnoreCase("addkeys")) {
                int keys;
                try {
                    keys = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§6Сервер: §cНекорректный формат числа");
                    return false;
                }
                @NonNls Player player = Bukkit.getPlayer(args[0]);
                UUID uuid;
                if (player == null || !player.isOnline()) {
                    uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
                } else {
                    uuid = player.getUniqueId();
                    player.sendMessage("§6Сервер: §fВам было начислено: §e" + keys + "§f ключей");
                }
                Optional<GamePlayer> optional = AccountAPI.getIfLoaded(uuid);
                if (optional.isPresent()) {
                    optional.get().addKeys(keys);
                    sender.sendMessage("§6Сервер: §fВы начислили игроку ключей");
                } else {
                    sender.sendMessage("§cДанный игрок еще не заходил на сервер");
                }
            }
        } else if(args.length == 0) {
            if(Bukkit.getPlayer(sender.getName()) == null) {
                sender.sendMessage("§cКоманда только для игроков");
                return false;
            }
            @NonNls Player p = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("money")) {
                AccountAPI.getIfLoaded(p.getUniqueId())
                        .ifPresent(e -> p.sendMessage("§6Сервер: §fНа вашем балансе: §a" + e.getMoney()));
            } else if (cmd.getName().equalsIgnoreCase("prefix")) {
                p.sendMessage("§cСкоро");
            } else if (cmd.getName().equalsIgnoreCase("lvl")) {
                AccountAPI.getIfLoaded(p.getUniqueId()).ifPresent(e -> {
                    p.sendMessage("Ваш уровень: §d" + e.getLevel());
                    p.sendMessage("Exp: §d" + e.getExp());
                    p.sendMessage("До следующего уровня: §d" + e.getExpNextLevel());
                });
            }
        }
        return false;
    }
}