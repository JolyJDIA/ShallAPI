package jolyjdia;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import jolyjdia.actionbar.ActionBarAPI;
import jolyjdia.actionbar.ActionBarAPIImpl;
import jolyjdia.api.boards.ScoreBoardAPI;
import jolyjdia.api.command.CommandHandler;
import jolyjdia.api.database.MySqlExecutor;
import jolyjdia.chat.ChatMain;
import jolyjdia.commands.CommandServer;
import jolyjdia.connector.Initializer;
import jolyjdia.nms.interfaces.NmsManager;
import jolyjdia.nms.interfaces.packet.PacketContainer;
import jolyjdia.nms.v1_15_R1.NmsManager_1_15;
import jolyjdia.nms.v1_15_R1.packet.PacketContainerImpl;
import jolyjdia.scoreboard.ScoreBoardAPIImpl;
import jolyjdia.utils.InitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class Main extends JavaPlugin {
    public static ScoreBoardAPI SCORE_BOARD_API;
    public static final PacketContainer PACKET_CONTAINER = new PacketContainerImpl();
    public static final ActionBarAPI ACTION_BAR_API = new ActionBarAPIImpl();
    public static final NmsManager NMS_API = new NmsManager_1_15();
    private static Main instance;
    private MySqlExecutor mySqlExecutor;
    private final Set<InitPlugin> modules = new HashSet<>();
    private final NioEventLoopGroup groupLoop = new NioEventLoopGroup();
    private Channel channel;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public final void onEnable() {
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(groupLoop)
                    .channel(NioDatagramChannel.class)
                    .handler(new Initializer());
            channel = bootstrap.connect("localhost", 8080).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.loadModule();
        this.modules.forEach(InitPlugin::onEnable);
        Bukkit.getPluginManager().registerEvents(new MainEvents(this), this);
        CommandHandler.registerCommand(new CommandServer());
        SCORE_BOARD_API = new ScoreBoardAPIImpl();
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
                if(mySqlExecutor != null) {
                    mySqlExecutor.close();
                }
            }
        }
    }
    public static Main getInstance() {
        return instance;
    }

    public MySqlExecutor initMySqlExecutor(MySqlExecutor executor) {
        if(this.mySqlExecutor == null) {
            this.mySqlExecutor = executor;
        }
        return mySqlExecutor;
    }

    public MySqlExecutor getMySqlExecutor() {
        return mySqlExecutor;
    }

    public Channel getChannel() {
        return channel;
    }
    private void loadModule() {
        registerModule(new ChatMain(this));
    }
    private void registerModule(InitPlugin module) {
        modules.add(module);
    }
}
