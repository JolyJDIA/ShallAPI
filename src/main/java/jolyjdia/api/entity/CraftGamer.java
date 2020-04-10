package jolyjdia.api.entity;

import jolyjdia.api.player.IBaseGamer;
import jolyjdia.api.skin.Skin;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public interface CraftGamer {
    Player getPlayer();//убрал Optional тк добавил класс OfflineGamer

    //String getDisplayName();

    Map<UUID, Friend> getFriends();
    boolean isFriend(UUID playerID);
    boolean isFriend(IBaseGamer gamer);
    boolean isFriend(Player player);
    boolean isFriend(Friend friend);
    int getFriendsLimit();
    ChatColor getPrefixColor();
    void sendMessage(BaseComponent... components);
    void sendMessage(String msg);
    void playSound(Sound sound);
    void playSound(Sound sound, float volume, float pitch);
    Skin getSkin();
    void setSkin(String name);
    void resetSkin();
}