package jolyjdia.commands;

import jolyjdia.Main;
import jolyjdia.api.command.CommandExecutors;
import jolyjdia.api.command.CommandLabel;
import jolyjdia.api.skin.Skin;
import jolyjdia.api.skin.SkinAPI;
import jolyjdia.utils.BukkitUtils;
import jolyjdia.utils.MathUtils;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
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
                Main.NMS_API.setSkin(p, Skin.DEFAULT);
            } else {
                String skinName = getArgs()[0];
                e.setSkin(skinName);
                SkinAPI.getSkinAsync(skinName).thenAccept(s -> Main.NMS_API.setSkin(p, s));
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
    @CommandLabel(alias = "fw", groupLvl = 1)
    public void firework() {
        getPlayer().ifPresent(p -> {
            FireworkEffect.Type[] types = FireworkEffect.Type.values();
            for(byte i = 0; i < 3; ++i) {
                Location loc = p.getLocation();
                if (loc.getWorld() == null) {
                    return;
                }
                Firework fw = loc.getWorld().spawn(loc, Firework.class);
                FireworkMeta fm = fw.getFireworkMeta();

                FireworkEffect.Type type = types[MathUtils.RANDOM.nextInt(types.length)];

                FireworkEffect effect = FireworkEffect.builder()
                        .flicker(MathUtils.RANDOM.nextBoolean())
                        .withColor(BukkitUtils.getColour(MathUtils.RANDOM.nextInt(15)))
                        .withFade(BukkitUtils.getColour(MathUtils.RANDOM.nextInt(15)))
                        .with(type)
                        .trail(MathUtils.RANDOM.nextBoolean())
                        .build();
                fm.addEffect(effect);
                fm.setPower(MathUtils.RANDOM.nextInt(2) + 1);
                fw.setFireworkMeta(fm);
            }
        });
    }
    /*@CommandLabel(alias = "npc", minArg = -1, maxArg = -1)
    public void createNpc() {
        getPlayer().ifPresent(p -> {
            Main.NMS_API.createDEntity(DEntityVillagerImpl.class, p.getLocation());
        });
    }*/

    @NotNull
    @NonNls
    private static String format(double tps) {
        return (tps > 18.0D ? ChatColor.GREEN : (tps > 16.0D ? ChatColor.YELLOW : ChatColor.RED))
                + (Math.min(Math.round(tps * 100.0D) / 100.0D, 20.0D) + (tps > 20.0D ? "+" : ""));
    }
}
