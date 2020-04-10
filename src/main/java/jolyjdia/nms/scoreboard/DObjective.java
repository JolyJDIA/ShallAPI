package jolyjdia.nms.scoreboard;

public class DObjective {

    private final String name;
    private String displayName;
    private final ObjectiveType type;

    public DObjective(String name, String displayName, ObjectiveType type) {
        this.name = name;
        this.displayName = displayName;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ObjectiveType getType() {
        return type;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
