package jolyjdia.api.player;

import jolyjdia.api.constant.GroupImp;

import java.util.UUID;

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
