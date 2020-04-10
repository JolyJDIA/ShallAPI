package jolyjdia.nms.scoreboard;

public class DScore {

    private final String name;
    private final DObjective dObjective;
    private final int score;

    public DScore(String name, DObjective dObjective, int score) {
        this.name = name;
        this.dObjective = dObjective;
        this.score = score;
    }

    @Override
    public String toString() {
        return "DScore{name=" + name + ", score=" + score +"}";
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public DObjective getDObjective() {
        return dObjective;
    }
}
