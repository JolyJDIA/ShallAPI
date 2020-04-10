package jolyjdia.api.player;

import jolyjdia.api.permission.Group;
import java.util.UUID;

@Deprecated
public interface IBaseGamer {
    UUID getUUID();
    int getPlayerID();
    //Skin getSkin();

    Group getGroup();
    void setGroup(Group group);
    default String getPrefix() {
        return getGroup().getPrefix();
    }

    int getExp();
    void addExp(int count);
    void subtractExp(int count);
    int getLevel();
    void addLevel(int count);
    default int getExpNextLevel() {
        return getLevel() * 25;
    }

    int getMoney();
    void addMoney(int count);
    void subtractMoney(int count);

    int getKeys();
    void addKeys(int count);
    void subtractKeys(int count);
}
