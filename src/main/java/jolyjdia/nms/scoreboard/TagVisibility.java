package jolyjdia.nms.scoreboard;

public enum TagVisibility {
    ALWAYS("always", 0),
    NEVER("never", 1),
    HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
    HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

    private final String value;
    private final int type;

    TagVisibility(String value, int type) {
        this.value = value;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
