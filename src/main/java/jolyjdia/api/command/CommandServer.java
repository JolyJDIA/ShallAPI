package jolyjdia.api.command;

import jolyjdia.Main;
import jolyjdia.api.skin.Skin;
import jolyjdia.api.skin.SkinAPI;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandServer extends CommandExecutors {
    @CommandLabel(alias = {"lag", "memory"}, maxArg = -1)
    public void lag() {
        Runtime runtime = Runtime.getRuntime();
        StringBuilder sb = new StringBuilder();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        sb.append("\n§fJava version: §c").append(Runtime.version())
                .append("\n§fПамять:")
                .append("\n §7Max: §a").append(runtime.maxMemory() / 1048576L).append(" МB")
                .append("\n §7Total: §a").append(totalMemory / 1048576L).append(" МB")
                .append("\n §7Use: §6").append((totalMemory - freeMemory) / 1048576L).append(" МB")
                .append(" §a(").append(freeMemory / 1048576L).append(" MB свободно)")
                .append("\n§fTPS за последние: \n §71мин  5мин  15мин: ")
                .append("\n ");
        double[] tps = MinecraftServer.getServer().recentTps;
        int iMax = tps.length - 1;
        for(int i = 0; ; ++i) {
            sb.append(format(tps[i]));
            if(i == iMax) {
                break;
            }
            sb.append(' ');
        }
        getSender().sendMessage(sb.toString());
    }
    @CommandLabel(alias = "skin", usage = "/skin <ник/reset> - §7изменить скин", minArg = 1, maxArg = 1)
    public void skin() {
        getGamePlayer().ifPresent(e -> getPlayer().ifPresentOrElse(p -> {
            if(getArgs()[0].equalsIgnoreCase("reset")) {
                e.resetSkin();
                Main.NMS_API.setSkin(p, Skin.DEFAULT.getValue(), Skin.DEFAULT.getSignature());
            } else {
                String skinName = getArgs()[0];
                e.setSkin(skinName);
                SkinAPI.getSkinAsync(skinName).thenAccept(s -> Main.NMS_API.setSkin(p, s.getValue(), s.getSignature()));
            }
            p.sendMessage("§6Сервер: §fСкин изменен");
        }, () -> getSender().sendMessage("Команда только для игроков")));
    }
    @CommandLabel(alias = "spawn", maxArg = -1)
    public void spawnTp() {
        getPlayer().ifPresent(p -> p.teleport(Objects.requireNonNull(p.getLocation().getWorld()).getSpawnLocation()));
    }
    @CommandLabel(alias = "setspawn", groupLvl = 5, maxArg = -1)
    public void setSpawn() {
        getPlayer().ifPresent(p -> {
            Objects.requireNonNull(p.getLocation().getWorld()).setSpawnLocation(p.getLocation());
            p.sendMessage("Локация спавна установлена");
        });
    }
    @CommandLabel(alias = "worlds", groupLvl = 4)
    public void worldList() {
        StringBuilder sb = new StringBuilder("Миры:");
        for(World zaWardo : Bukkit.getWorlds()) {
            sb.append("\n §a").append(zaWardo.getName());
        }
        getSender().sendMessage(sb.toString());
    }
    @CommandLabel(alias = "world", usage = "/world <мир> - §7телепортировать в мир", groupLvl = 5, minArg = 1, maxArg = 1)
    public void worldTp() {
        World world = Bukkit.getWorld(getArgs()[0]);
        if(world == null) {
            getSender().sendMessage("Данного мира не существует: /worlds");
        } else {
            getPlayer().ifPresent(e -> e.teleport(world.getSpawnLocation()));
        }
    }

    @NotNull
    @NonNls
    private static String format(double tps) {
        return (tps > 18.0D ? ChatColor.GREEN : (tps > 16.0D ? ChatColor.YELLOW : ChatColor.RED))
                + (Math.min(Math.round(tps * 100.0D) / 100.0D, 20.0D) + (tps > 20.0D ? "+" : ""));
    }
}
