package jolyjdia.api.player.section;

import jolyjdia.api.player.IBaseGamer;

@Deprecated
public class GamerSection extends Section {
    private int money, lvl, exp, keys;

    public GamerSection(IBaseGamer iBaseGamer) {
        super(iBaseGamer);
    }

    public int getMoney() {
        return money;
    }

    public int getKeys() {
        return keys;
    }

    public int getExp() {
        return exp;
    }

    public int getLvl() {
        return lvl;
    }

    public void addExp(int exp) {
        this.exp += exp;
    }

    public void addKeys(int keys) {
        this.keys += keys;
    }

    public void addLvl(int lvl) {
        this.lvl += lvl;
    }

    public void addMoney(int money) {
        this.money += money;
    }
    public int getExpNextLevel() {
        return lvl * 25;
    }
}
