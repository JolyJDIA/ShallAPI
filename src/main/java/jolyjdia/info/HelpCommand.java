package jolyjdia.info;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements CommandExecutor {
    private final AutoMessage main;


    HelpCommand(@NotNull AutoMessage main) {
        this.main = main;
    }

    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            sender.sendMessage("Использование: §c/helps <Номер страницы>");
            return false;
        }
        if (args.length == 1) {
            try {
                int nowPag = Integer.parseInt(args[0]);
                int allPage = main.getHelp().size();
                if(nowPag <= 0) {
                    sender.sendMessage("§cВведите положительное число");
                    return false;
                }
                if(nowPag > main.getHelp().size()) {
                    sender.sendMessage("Всего страниц: §c"+allPage);
                    return false;
                }
                sender.sendMessage(
                        "\nСтраница §a" +(nowPag)+ '/' +allPage + '\n' +
                        main.getHelp().get(nowPag-1) +
                        "\n "
                );
            } catch (NumberFormatException e) {
                sender.sendMessage("§cВы неправильно ввели номер страницы помощи!");
                return false;
            }
        }
        return false;
    }
}
