package jolyjdia.api.player;

import jolyjdia.api.constant.GroupImpl;

import java.util.UUID;

public class GamerData implements IBaseGamer {
    private final int playerId;
    private final UUID uuid;
    private GroupImpl groupImp;
    private int money, exp, lvl, keys;

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
        this.groupImp = group;
    }

    @Override
    public int getExp() {
        return exp;
    }

    @Override
    public void addExp(int count) {
        this.exp += count;
    }


    @Override
    public int getLevel() {
        return lvl;
    }

    @Override
    public void addLevel(int count) {
        this.lvl += count;
    }

    @Override
    public int getMoney() {
        return money;
    }


    @Override
    public void addMoney(int count) {
        this.money += count;
    }

    @Override
    public int getKeys() {
        return keys;
    }

    @Override
    public void addKeys(int count) {
        this.keys += count;
    }
}