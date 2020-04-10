package jolyjdia.api.boards;

public enum Collides {
    ALWAYS("always"),
    PUSH_OTHER_TEAMS("pushOtherTeams"),
    PUSH_OWN_TEAMS("pushOwnTeam"),
    NEVER("never");

    private final String value;

    Collides(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
