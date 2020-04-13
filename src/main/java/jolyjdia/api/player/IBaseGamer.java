package jolyjdia.api.player;

import com.google.common.collect.ClassToInstanceMap;
import jolyjdia.api.constant.GroupImp;
import jolyjdia.api.permission.Group;
import jolyjdia.api.player.section.Section;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public interface IBaseGamer {
    UUID getUUID();
    int getPlayerID();
    //Skin getSkin();

    GroupImp getGroup();
    void setGroup(GroupImp group);
    default String getPrefix() {
        return getGroup().getPrefix();
    }

    int getExp();
    void addExp(int count);

    int getLevel();
    void addLevel(int count);
    default int getExpNextLevel() {
        return getLevel() * 25;
    }

    int getMoney();
    void addMoney(int count);

    int getKeys();
    void addKeys(int count);

    default boolean hasPermission(int lvl) {
        return getGroup().hasPermission(lvl);
    }
    default boolean noPermission(int lvl) {
        return getGroup().noPermission(lvl);
    }
}
