package jolyjdia.api.player;

import jolyjdia.api.constant.GroupImp;
import jolyjdia.api.permission.Group;

import java.util.UUID;

public class GamerData implements IBaseGamer {
    private final UUID uuid;
    private GroupImp groupImp;

    public GamerData(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getPlayerID() {
        return 0;
    }

    @Override
    public GroupImp getGroup() {
        return groupImp;
    }

    @Override
    public void setGroup(GroupImp group) {
        groupImp = group;
    }

    @Override
    public int getExp() {
        return 0;
    }

    @Override
    public void addExp(int count) {

    }


    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public void addLevel(int count) {

    }

    @Override
    public int getMoney() {
        return 0;
    }


    @Override
    public void addMoney(int count) {

    }

    @Override
    public int getKeys() {
        return 0;
    }

    @Override
    public void addKeys(int count) {

    }
}