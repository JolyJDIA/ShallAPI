package jolyjdia.api.command;

import jolyjdia.api.AccountAPI;
import jolyjdia.api.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class CommandHandler {
    private static final CommandMap COMMAND_MAP = ((CraftServer) Bukkit.getServer()).getCommandMap();

    private CommandHandler() {}

    public static void registerCommand(@NotNull CommandExecutors command) {
        for (Method method : command.getClass().getMethods()) {
            if (!method.isAnnotationPresent(CommandLabel.class)) {
                continue;
            }
            if (method.getParameterTypes().length > 0) {
                continue;
            }
            CommandLabel label = method.getAnnotation(CommandLabel.class);
            Handler handler = new Handler(() -> {
                try {
                    method.invoke(command);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }, command, label.alias(), label.usage(), label.desc(), label.groupLvl(), label.minArg(), label.maxArg());
            handler.setPermission(label.permission());
            handler.setPermissionMessage(label.noPermissionMsg());
            COMMAND_MAP.register(label.alias()[0], handler);
        }
    }

    public static CommandMap getCommandMap() {
        return COMMAND_MAP;
    }

    public static class Handler extends Command {
        private final Runnable runnable;
        private final CommandExecutors consumer;
        private final int groupLvl, minArg, maxArg;

        Handler(Runnable runnable,
                CommandExecutors consumer,
                String @NotNull [] alias,
                String usage,
                String desc,
                int groupLvl,
                int minArg,
                int maxArg
        ) {
            super(alias[0], desc, usage, Arrays.asList(alias));
            this.runnable = runnable;
            this.consumer = consumer;
            this.groupLvl = groupLvl;
            this.minArg = minArg;
            this.maxArg = maxArg;
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
            if(sender instanceof Player) {
                GamePlayer accountAPI = AccountAPI.get(((Entity)sender).getUniqueId());
                if(accountAPI != null && accountAPI.getGroup().getStar() < groupLvl) {
                    sender.sendMessage("\n§cУ вас нет разрешений на доступ к этой команде\n ");
                    return false;
                }
            }
            if((args.length >= minArg && args.length <= maxArg) || maxArg == -1) {
                consumer.accept(sender, args);
                runnable.run();
            } else {
                if(!getUsage().isEmpty()) {
                    sender.sendMessage(getUsage());
                }
                return false;
            }
            return true;
        }
    }
}
