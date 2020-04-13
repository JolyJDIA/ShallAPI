package jolyjdia.api.command;

import jolyjdia.api.AccountAPI;
import jolyjdia.api.player.GamePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class CommandExecutors implements ConsumerCommand {
    private CommandSender sender;
    private String[] args;

    @Override
    public void accept(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String[] getArgs() {
        return args;
    }
    public Optional<GamePlayer> getGamePlayer() {
        if(sender instanceof Player) {
            return AccountAPI.getIfLoaded(((Entity) sender).getUniqueId());
        }
        return Optional.empty();
    }
    public Optional<Player> getPlayer() {
        if(sender instanceof Player) {
            return Optional.of((Player)sender);
        }
        return Optional.empty();
    }
}
