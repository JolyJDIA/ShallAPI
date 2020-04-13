package jolyjdia.api.player;

import jolyjdia.api.entity.CraftGamer;
import jolyjdia.api.entity.Friend;
import jolyjdia.api.skin.Skin;
import jolyjdia.api.skin.SkinAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class GamePlayer extends GamerData implements CraftGamer {
    private Player player;
    private Skin skin;
    public GamePlayer(UUID uuid) {
        super(uuid);
    }

    @Override
    public final Player getPlayer() {
        if(player == null) {
            player = Bukkit.getPlayer(getUUID());
        }
        return player;
    }

    @Override
    public Map<UUID, Friend> getFriends() {
        return null;
    }

    @Override
    public boolean isFriend(UUID playerID) {
        return false;
    }

    @Override
    public boolean isFriend(IBaseGamer gamer) {
        return false;
    }

    @Override
    public boolean isFriend(Player player) {
        return false;
    }

    @Override
    public boolean isFriend(Friend friend) {
        return false;
    }

    @Override
    public int getFriendsLimit() {
        return 0;
    }

    @Override
    public ChatColor getPrefixColor() {
        return null;
    }

    @Override
    public void sendMessage(BaseComponent... components) {
        player.spigot().sendMessage(components);
    }

    @Override
    public void sendMessage(String msg) {
        player.sendMessage(msg);
    }

    @Override
    public void playSound(Sound sound) {
        player.getWorld().playSound(player.getLocation(), sound, 1, 0);
    }

    @Override
    public void playSound(Sound sound, float volume, float pitch) {
        player.getWorld().playSound(player.getLocation(), sound, volume, pitch);
    }

    @Override
    public Skin getSkin() {
        return skin;
    }

    @Override
    public void setSkin(String name) {
        this.skin = SkinAPI.getSkin(name);//todo: completedfuture
    }

    @Override
    public void resetSkin() {

    }
}
