package jolyjdia.api.command;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface ConsumerCommand {
    void accept(CommandSender sender, String[] args);
}
