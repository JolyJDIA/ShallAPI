package jolyjdia.api.player;

import jolyjdia.api.constant.GroupImpl;

import java.util.UUID;

public class GamerData implements IBaseGamer {
    private final int playerId;
    private final UUID uuid;
    private GroupImpl groupImp;

    public GamerData(int playerId, UUID uuid) {
        this.uuid = uuid;
        this.playerId = playerId;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getPlayerID() {
        return playerId;
    }

    @Override
    public GroupImpl getGroup() {
        return groupImp;
    }

    @Override
    public void setGroup(GroupImpl group) {
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