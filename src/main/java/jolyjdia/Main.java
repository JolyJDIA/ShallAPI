package jolyjdia;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import jolyjdia.api.command.CommandHandler;
import jolyjdia.api.command.CommandServer;
import jolyjdia.api.command.ServerCommands;
import jolyjdia.api.permission.PermissionManager;
import jolyjdia.chat.ChatMain;
import jolyjdia.clear.ClearLag;
import jolyjdia.netty.listener.UDPUpstreamHandler;
import jolyjdia.nms.interfaces.NmsManager;
import jolyjdia.nms.interfaces.packet.PacketContainer;
import jolyjdia.nms.v1_15_R1.NmsManager_1_15;
import jolyjdia.nms.v1_15_R1.packet.PacketContainerImpl;
import jolyjdia.utils.InitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Main extends JavaPlugin {
    //ПЕРЕНЕСТИ
    public static final PacketContainer PACKET_CONTAINER = new PacketContainerImpl();
    public static final NmsManager NMS_API = new NmsManager_1_15();
    private static Main instance;
    private final Set<InitPlugin> modules = new HashSet<>();
    private Channel channel;
    private final NioEventLoopGroup groupLoop = new NioEventLoopGroup();

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public final void onEnable() {
        PermissionManager.calculatePermission(this);
        this.loadModule();
        this.modules.forEach(InitPlugin::onEnable);
        Bukkit.getPluginManager().registerEvents(new MainEvents(this), this);
        CommandHandler.registerCommand(new CommandServer());
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(groupLoop)
                    .channel(NioDatagramChannel.class)
                    .handler(new UDPUpstreamHandler());
            channel = bootstrap.connect("localhost", 8080).sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void onDisable() {
        try {
            Bukkit.getOnlinePlayers().forEach(e -> e.kickPlayer("Я вырубил сервачок, перезайди крч"));
            modules.forEach(InitPlugin::onDisable);
        } finally {
            try {
                groupLoop.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("isShutdown: "+groupLoop.isShutdown() + " Channel open: "+channel.isOpen());
            }
            //todo mysql close
        }
    }


    private void loadModule() {
        registerModule(new ClearLag(this));
        registerModule(new ChatMain(this));
    }
    private void registerModule(InitPlugin module) {
        modules.add(module);
    }

    public Channel getChannel() {
        return channel;
    }
}
