package jolyjdia.api.player;

import jolyjdia.api.permission.Group;

import java.util.UUID;

@Deprecated
public class GamerData implements IBaseGamer {
    private final UUID uuid;

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
    public Group getGroup() {
        return null;
    }

    @Override
    public void setGroup(Group group) {

    }

    @Override
    public int getExp() {
        return 0;
    }

    @Override
    public void addExp(int count) {

    }

    @Override
    public void subtractExp(int count) {

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
    public void subtractMoney(int count) {

    }

    @Override
    public int getKeys() {
        return 0;
    }

    @Override
    public void addKeys(int count) {

    }

    @Override
    public void subtractKeys(int count) {

    }
}