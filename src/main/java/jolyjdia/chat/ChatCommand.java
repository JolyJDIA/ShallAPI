package jolyjdia.chat;

import jolyjdia.api.command.CommandExecutors;
import jolyjdia.api.command.CommandLabel;
import jolyjdia.utils.BukkitUtils;

import java.nio.charset.StandardCharsets;

public class ChatCommand extends CommandExecutors {


    @CommandLabel(alias = "chat", minArg = 1, maxArg = 1, groupLvl = 5, usage = "/chat clear - §7очистить чат")
    public void chat() {
        if(getArgs()[0].equalsIgnoreCase("clear")) {
            final int limit = 200;
            final byte[] multiple = new byte[limit];
            System.arraycopy(" \n".getBytes(), 0, multiple, 0, 2);
            int copied = 2;
            for (; copied < limit - copied; copied <<= 1) {
                System.arraycopy(multiple, 0, multiple, copied, copied);
            }
            System.arraycopy(multiple, 0, multiple, copied, limit - copied);
            BukkitUtils.messagesPlayers(new String(multiple, StandardCharsets.UTF_8));
        }
    }
}
